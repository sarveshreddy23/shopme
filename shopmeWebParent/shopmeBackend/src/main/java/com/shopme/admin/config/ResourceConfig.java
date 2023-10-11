package com.shopme.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String dir = "user-photos";
        Path pathDir = Paths.get(dir);

        registry.addResourceHandler("/user-photos/**")
                .addResourceLocations("file:/"+pathDir.toFile().getAbsolutePath()+"/");
    }
}
