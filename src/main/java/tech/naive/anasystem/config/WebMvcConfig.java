package tech.naive.anasystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.naive.anasystem.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor getInterceptor(){
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptor())
                .addPathPatterns("/api/form/**")
                .addPathPatterns("/api/user/deleteUser")
                .addPathPatterns("/api/user/editUser")
                .addPathPatterns("/api/user/changePassword")
                .addPathPatterns("/api/user/changeRealName")
                .addPathPatterns("/api/user/getUser")
                .addPathPatterns("/api/user/getUsers")
        ;
    }
}
