package tech.naive.anasystem.config;

import org.springframework.context.annotation.Configuration;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/form/**")
        ;
    }
}
