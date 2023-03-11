package com.nimshub.biobeacon.config;

import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import com.nimshub.biobeacon.exceptions.FilterChainExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
    private final DeviceRepository deviceRepository;
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException, DeviceNotFoundException {

        final String deviceId = request.getHeader("X-Device-Id");
        final String apiKey = request.getHeader("X-Api-Key");
        Device device;

        if (deviceId.isBlank()) {
            filterChainExceptionHandler.handelException(
                    new BadCredentialsException("Device ID was not included in the header"), response, request);
            logger.warn("Device ID was not included in the header");
            return;
        }
        try {
            device = deviceRepository.findById(Long.parseLong(deviceId))
                    .orElseThrow(() ->
                            new DeviceNotFoundException("Device with id : [%s] Not Found".formatted(deviceId)));
        } catch (DeviceNotFoundException e) {
            filterChainExceptionHandler.handelException(e, response, request);
            logger.warn("Device not Not Found");
            return;
        }

        if (device.getApiKey().equals(apiKey)) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChainExceptionHandler.handelException(
                new BadCredentialsException("Invalid API Key"), response, request);
        logger.warn("Invalid API Key");
    }
}
