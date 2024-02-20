package com.naraci.core.config;

import com.naraci.core.aop.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

@Bean
    public TokenInterceptor needTokenInterceptor() {
    return new TokenInterceptor();
}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录处理拦截器，拦截所有请求，登录请求除外
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(needTokenInterceptor());

        // 接口文档排除
        interceptorRegistration.excludePathPatterns("/sys/login.json");
        interceptorRegistration.excludePathPatterns("/charts/**");
        interceptorRegistration.excludePathPatterns("/css/**");
        interceptorRegistration.excludePathPatterns("/easyUi/**");
        interceptorRegistration.excludePathPatterns("/flashPlayer/**");
        interceptorRegistration.excludePathPatterns("/font/**");
        interceptorRegistration.excludePathPatterns("/images/**");
        interceptorRegistration.excludePathPatterns("/js/**");
        interceptorRegistration.excludePathPatterns("/pages/**");
        interceptorRegistration.excludePathPatterns("/plugin/**");
        interceptorRegistration.excludePathPatterns("/index.html");
        interceptorRegistration.excludePathPatterns("/show.html");
        interceptorRegistration.excludePathPatterns("/**/doc.html");
        interceptorRegistration.excludePathPatterns("/**/swagger-resources/**");
        interceptorRegistration.excludePathPatterns("/**/webjars/**");
        interceptorRegistration.excludePathPatterns("/**/error/**");
        interceptorRegistration.excludePathPatterns("/**/v3/api-docs/swagger-config/**");
        interceptorRegistration.excludePathPatterns("/**/v3/api-docs/**");
        interceptorRegistration.excludePathPatterns("/**/login");
        interceptorRegistration.excludePathPatterns("/**/register");
        interceptorRegistration.excludePathPatterns("/sendMessage");
        //配置拦截策略
        interceptorRegistration.addPathPatterns("/**");
    }

    @Bean
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST","GET","OPTIONS","PUT","DELETE")
                .allowCredentials(true);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(httpMessageConverter -> httpMessageConverter.getClass() == StringHttpMessageConverter.class);
    }
}
