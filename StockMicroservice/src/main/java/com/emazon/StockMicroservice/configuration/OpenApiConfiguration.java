package com.emazon.StockMicroservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the OpenAPI documentation for the Stock Microservice.
 * Sets up basic API information like title, version, and description.
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI StockMicroserviceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stock Microservice")
                        .version("1.0")
                        .description("API documentation for the Stock Microservice"));
    }

}
