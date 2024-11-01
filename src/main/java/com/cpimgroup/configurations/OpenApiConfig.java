package com.cpimgroup.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Usuarios CRUD",
                version = "1.0.0",
                description = "Este es un CRUD para administrar Usuarios"
        )
)
public class OpenApiConfig {}
