package pl.coderslab.betok;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/logout").setViewName("logoutView");
//        registry.addViewController("/login").setViewName("loginView");


        registry.addViewController("/").setViewName("Hello");
        registry.addViewController("/home").setViewName("Home");
        registry.addViewController("/admin/panel").setViewName("admin/AdminPanel");
        registry.addViewController("/test").setViewName("Test");
    }
}