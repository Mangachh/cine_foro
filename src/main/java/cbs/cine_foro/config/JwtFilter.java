package cbs.cine_foro.config;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cbs.cine_foro.service.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String STARTS_WITH = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "authorization";
    
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;
        final String userName;
        
        if (authHeader == null || authHeader.startsWith(STARTS_WITH) == false) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(STARTS_WITH.length() + 1);
        userName = jwtService.extractUserName(jwt); // TODO: extract userName;
    }
    
}
