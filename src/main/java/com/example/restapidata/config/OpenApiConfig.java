package com.example.restapidata.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My Awesome API",
                version = "1.0",
                description = "Documentation for all my endpoints"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-public")
                .pathsToMatch("/api/public/**", "/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi securedApi() {
        return GroupedOpenApi.builder()
                .group("02-Secured")
                .pathsToMatch("/api/**")
                //.pathsToExclude("/api/public/**", "/api/auth/login")
                .addOperationCustomizer(securityCustomizer())
                .build();
    }

    private OperationCustomizer securityCustomizer() {
        return (operation, handlerMethod) -> {
            io.swagger.v3.oas.models.security.SecurityRequirement requirementModel =
                    new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth");
            operation.addSecurityItem(requirementModel);
            return operation;
        };
    }

}