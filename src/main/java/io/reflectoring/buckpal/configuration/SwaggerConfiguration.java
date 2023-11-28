package io.reflectoring.buckpal.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Universe Swagger", description = "Swagger Description", version = "v1"))
@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT"
)
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi GroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Group API v1")
                .pathsToMatch("/**")
                .build();
    }
}
