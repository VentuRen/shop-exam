package com.test.interview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 🔹 Aplica a todas las rutas
                        .allowedOrigins("http://localhost:5173") // 🔹 Permitir desde React
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 🔹 Métodos permitidos
                        .allowedHeaders("*") // 🔹 Permitir todos los headers
                        .allowCredentials(true); // 🔹 Permitir credenciales (cookies, headers auth)
            }
        };
    }
}
