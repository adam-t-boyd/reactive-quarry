package com.adamboyd.reactive.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/** Configuration of OpenAPI Swagger UI **/
@Configuration
public class SwaggerConfiguration {

    /** Configuration of swagger page and version **/
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Adam Boyd's Reactive Java Application"))
                .specVersion(SpecVersion.valueOf("1.0"));
    }
}
