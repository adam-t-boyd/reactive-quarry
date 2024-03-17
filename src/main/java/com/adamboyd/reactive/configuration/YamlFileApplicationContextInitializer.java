package com.adamboyd.reactive.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@ConfigurationProperties
public class YamlFileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Resource resource = applicationContext.getResource("classpath:file.yml");
            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
            List<PropertySource<?>> yamlTestProperties = sourceLoader.load("yamlTestProperties", resource);

            yamlTestProperties.forEach(propertySource -> {
                applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}