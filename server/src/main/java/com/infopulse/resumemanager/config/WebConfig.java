package com.infopulse.resumemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        String home = System.getProperty("user.home");
        String path = "File:" + home + File.separator + "resumes" + File.separator;
        System.out.println(path);

        registry
                .addResourceHandler("/api/v1/resumes/**")
                .addResourceLocations(path);
    }
}
