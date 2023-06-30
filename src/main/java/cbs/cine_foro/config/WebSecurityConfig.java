package cbs.cine_foro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private static final boolean IS_DEBUG = true;
    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] WHITE_LIST = { "read/**", "auth/**", "console/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // return this.simpleGetApply(http);
        return this.permitWhiteList(http);
        //return this.permitAll(http);

    }
    
    private SecurityFilterChain permitAll(HttpSecurity http) throws Exception {
         http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(r -> r.anyRequest().permitAll());
         return http.build();
    }
    
    private SecurityFilterChain permitWhiteList(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(r -> r.requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    /**
     * Any user can read all the GET requests
     * @param http
     * @return
     * @throws Exception
     */
    private SecurityFilterChain simpleGetApply(HttpSecurity http) throws Exception {
        // woooo it works
        if (!IS_DEBUG) {
            http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(r -> r.requestMatchers(HttpMethod.GET).permitAll());
        } else {
            http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(r -> r.anyRequest().permitAll());
        }

        return http.build();
    }
}
