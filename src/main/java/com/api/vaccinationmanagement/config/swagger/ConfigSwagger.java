package com.api.vaccinationmanagement.config.swagger;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Thiết lập các server dùng để test api
                .servers(Lists.newArrayList(new Server().url("http://localhost:8080")))
                // info
                .info(new Info().title("API - Vaccination management")
                        .description("OpenAPI 3.0")
                        .contact(new Contact()
                                .email("goldenv@gmail.com")
                                .name("Vo~"))
                        .version("1.0.0"));
    }
}