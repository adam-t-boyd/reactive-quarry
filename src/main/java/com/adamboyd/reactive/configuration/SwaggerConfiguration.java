package com.adamboyd.reactive.configuration;

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.TreeMap;


/** Configuration of OpenAPI Swagger UI **/
@Configuration
public class SwaggerConfiguration {

    /**
     * Alphabetically sorts the schemas
     */
    @Bean
    public OpenApiCustomizer sortTagsAlphabetically() {
        return openApi -> {
            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
            openApi.getComponents().setSchemas(new TreeMap<>(schemas));};
    }
}
