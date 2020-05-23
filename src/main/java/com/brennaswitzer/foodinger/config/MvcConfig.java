package com.brennaswitzer.foodinger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // it's unclear why this needs to be re-declared
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
