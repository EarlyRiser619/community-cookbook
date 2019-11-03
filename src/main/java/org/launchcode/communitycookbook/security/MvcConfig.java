package org.launchcode.communitycookbook.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/recipe/index.html").setViewName("home");
        registry.addViewController("/recipe/add.html").setViewName("recipeAdd");
        registry.addViewController("/user/index.html").setViewName("userHome");
        registry.addViewController("/user/login.html").setViewName("login");
        registry.addViewController("/user/add.html").setViewName("register");
    }
}
