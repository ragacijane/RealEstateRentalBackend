package com.project.RealEstateRental;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.directory.path}")
    private String imageDirectoryPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Use the directory path from application.properties
        String filePath = "file:///" + imageDirectoryPath + "/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(filePath);
    }
}
