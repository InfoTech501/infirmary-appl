package com.rocs.infirmary.application.security.configuration;

import com.rocs.infirmary.application.security.jwt.filter.authentication.access.denied.JwtAccessDeniedHandler;
import com.rocs.infirmary.application.security.jwt.filter.authentication.forbidden.AuthenticationEntryPoint;
import com.rocs.infirmary.application.security.jwt.filter.authorization.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.rocs.infirmary.application.security.utils.constants.SecurityConstant.PUBLIC_URLS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 AuthenticationEntryPoint authenticationEntryPoint,
                                 @Qualifier(value = "userDetailsService")
                                 UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 AuthenticationConfiguration authenticationConfiguration) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationConfiguration = authenticationConfiguration;
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of("https://localhost:8080"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated()).
                exceptionHandling(e -> {
                    e.authenticationEntryPoint(authenticationEntryPoint);
                    e.accessDeniedHandler(jwtAccessDeniedHandler);
                }).addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
