package ru.binarysimple.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import ru.binarysimple.users.filter.UserAuthFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public UserAuthFilter userAuthFilter() {
        return new UserAuthFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/user/**")
                .authenticated()
                .requestMatchers("/actuator/**").permitAll()
                // Разрешаем Swagger UI и API docs
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**"
                ).permitAll());
        http.headers(Customizer.withDefaults());
        http.anonymous(Customizer.withDefaults());
//        http.csrf(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());  // Отключаем CSRF для API

        configureFilters(http);

        return http.build();
    }

    private void configureFilters(HttpSecurity http) {
        http
                .addFilterBefore(userAuthFilter(), AuthorizationFilter.class);
    }
}