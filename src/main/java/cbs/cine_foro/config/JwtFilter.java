package cbs.cine_foro.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private static final String STARTS_WITH = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
        final String header = request.getHeader(AUTHORIZATION_HEADER);
        // used in the tuto    

        final String userEmail;
        final String jwtToken;

        if (header == null || header.startsWith(STARTS_WITH) == false) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = header.substring(STARTS_WITH.length() + 1);

        // again on tuto
        userEmail = "myMail"; // TODO: to be implemented
        
    }
    
}
