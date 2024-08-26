package com.example.test1.configuration;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI(
            @Value("${open.api.title}") String title,
            @Value("${open.api.version}") String version,
            @Value("${open.api.description}") String description,
            @Value("${open.api.serverURL}") String serverURL,
            @Value("${open.api.serverName}") String serverName) {
        return new OpenAPI()
                .info(new Info()
                        .title("API-service document")
                        .version("v1.0.0")
                        .description("description")
                        .license(new License().name("API License").url("http://domain.vn/license")))
                .servers(List.of(new Server().url(serverURL).description(serverName)));
        //                .components(
        //                        new Components()
        //                                .addSecuritySchemes(
        //                                        "Auth",
        //                                        new SecurityScheme()
        //                                                .type(SecurityScheme.Type.HTTP)
        //                                                .scheme("bearer")
        //                                                .bearerFormat("JWT")))
        //                .security(List.of(new SecurityRequirement().addList("Auth")));

    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("api-server-fresher")
                .packagesToScan("com..example.test1.controllers")
                .build();
    }
}
