package com.rocs.infirmary.application.utils.security.configuration;

import com.rocs.infirmary.application.utils.security.jwt.filter.authentication.access.denied.JwtAccessDeniedHandler;
import com.rocs.infirmary.application.utils.security.jwt.filter.authentication.forbidden.AuthenticationEntryPoint;
import com.rocs.infirmary.application.utils.security.jwt.filter.authorization.JwtAuthorizationFilter;
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

import static com.rocs.infirmary.application.utils.security.constants.SecurityConstant.PUBLIC_URLS;
/**
 * {@code SecurityConfiguration} provides the security configuration for the application, setting up authentication, authorization,
 *  and Cross-Origin Resource Sharing that allow or restrict access to the application’s resources from different domains.
 * */
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
    /**
     * Creates a new constructor for {@code SecurityConfiguration}
     *
     * @param jwtAuthorizationFilter filter the JWT token authorization
     * @param jwtAccessDeniedHandler handle the access denied errors
     * @param authenticationEntryPoint entry point for authentication failures
     * @param userDetailsService load the user details
     * @param bCryptPasswordEncoder password encoder (BCrypt)
     * @param authenticationConfiguration configuration for authentication management
     */
    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 AuthenticationEntryPoint authenticationEntryPoint,
                                 @Qualifier("userDetailsService")
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
    /**
     * Configures the AuthenticationManager with a user details service and password encoder.
     *
     * @param authenticationManagerBuilder the builder for the authentication manager
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Provides bean for {@code AuthenticationManager}
     * @return AuthenticationManager
     * */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /**
     * Provides bean and Configruation for CorsConfiguration that allow specific domain and headers
     * @return CorsConfigurationSource
     * */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://localhost:8080","http://localhost:8081","http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of(
                "Authorization",
                "x-xsrf-token",
                "Access-Control-Allow-Headers",
                "Jwt-Token",
                "Uid"));
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    /**
     * Prvides bean and Configuration for security filters, session management, and request authorizations.
     *
     * @param httpSecurity the security configuration for HTTP requests
     * @return the configured security filter chain
     * @throws Exception if an error occurs during security setup
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).
                cors(cors -> corsConfigurationSource()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated()).
                exceptionHandling(e -> {
                    e.authenticationEntryPoint(authenticationEntryPoint);
                    e.accessDeniedHandler(jwtAccessDeniedHandler);
                }).addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
