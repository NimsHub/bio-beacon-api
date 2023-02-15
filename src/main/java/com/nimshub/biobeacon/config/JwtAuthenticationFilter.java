package com.nimshub.biobeacon.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
@RequiredArgsConstructor // creates constructor using any private final fields in the class
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        token = authHeader.substring(7);
        userEmail= jwtService.extractUserName(token);
    }
}