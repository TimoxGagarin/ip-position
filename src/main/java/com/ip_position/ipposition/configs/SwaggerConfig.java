package com.ip_position.ipposition.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "IP-position", version = "0.3.0", license = @License(name = "MIT License", url = "https://github.com/TimoxGagarin/ip-position/blob/main/LICENSE.md"), contact = @Contact(name = "Rudko Timofey", url = "https://t.me/TimoxGagarin", email = "timarudkoooo@yandex.by")), servers = {
        @Server(url = "http://localhost:8080", description = "Local Server")
})
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs", "/swagger-ui.html");
    }
}