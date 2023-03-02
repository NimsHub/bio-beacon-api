package com.nimshub.biobeacon.config;

import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
    private final DeviceRepository deviceRepository;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String deviceId = request.getHeader("DeviceId");
        final String apiKey = request.getHeader("X-Api-Key");

            if(deviceId.isBlank()){
                response.getWriter().write("Device ID was not included in the header");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.warn("Device ID was not included in the header");
                return;
            }

        Device device = deviceRepository.findById(Long.parseLong(deviceId))
                .orElseThrow(()-> new DeviceNotFoundException("Device Not Found"));

        if(device.getApiKey().equals(apiKey)){
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write("Invalid API Key");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        logger.warn("Invalid API Key");
    }
}
