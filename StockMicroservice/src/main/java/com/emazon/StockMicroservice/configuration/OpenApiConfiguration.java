package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import com.emazon.StockMicroservice.configuration.util.ConfigConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI StockMicroserviceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(ConfigConstants.APP_TITLE)
                        .version(ConfigConstants.VERSION)
                        .description(ConfigConstants.APP_DESCRIPTION));
    }

}
