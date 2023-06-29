package cbs.cine_foro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity 
public class WebSecurityConfig {
    private static final boolean IS_DEBUG = true;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // woooo it works
        if (! IS_DEBUG) {
            http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(r -> r.requestMatchers(HttpMethod.GET).permitAll());
        } else {
            http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(r -> r.anyRequest().permitAll());
        }
        
        return http.build();
    }
}
