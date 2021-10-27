package tech.naive.anasystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.naive.anasystem.interceptor.LoginInterceptor;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 2:35 PM
 */
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
