package com.proffen.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "PROSTO SBER HACK API",
                description = "Sample API",
                version = "1.0.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Default Server URL")
        },
        tags = {
                @Tag(name = "Authorization and Registration", description = "API for auth and registration"),
                @Tag(name = "Member Management", description = "API for members"),

        }
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
}
