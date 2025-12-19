package com.javaLab.web.configs;

import com.javaLab.web.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Конфигурация безопасности Spring Security для веб-приложения.
 * <p>
 * Настраивает:
 * <ul>
 *   <li>Авторизацию по ролям (ADMIN, MODERATOR)</li>
 *   <li>Открытый доступ к /auth/**, /images/**, /news/getallnews</li>
 *   <li>CORS для localhost:5173 (фронтенд)</li>
 *   <li>BCrypt шифрование паролей</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Основная цепочка фильтров безопасности.
     * Отключает CSRF, настраивает авторизацию и CORS.
     *
     * @param http объект конфигурации HttpSecurity
     * @return настроенная цепочка фильтров
     * @throws Exception при ошибках конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**", "/images/**").permitAll()
                        .requestMatchers("/news/getallnews").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/news/**").hasAnyRole("MODERATOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    /**
     * Шифровщик паролей BCrypt.
     * Используется для хеширования паролей пользователей.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Менеджер аутентификации Spring Security.
     *
     * @param configuration конфигурация аутентификации
     * @return AuthenticationManager
     * @throws Exception при ошибках конфигурации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Провайдер аутентификации на основе UserDetailsService.
     * Связывает сервис пользователей с шифровщиком паролей.
     *
     * @return настроенный DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Конфигурация CORS для фронтенда на localhost:5173.
     * Разрешает все методы и заголовки с поддержкой credentials.
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
