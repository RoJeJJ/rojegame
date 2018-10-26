package com.roje.game.cluster.configuration;

import com.roje.game.cluster.interceptor.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultWebMvcAdapter implements WebMvcConfigurer {

    private final AuthHandlerInterceptor authHandlerInterceptor;

    @Autowired
    public DefaultWebMvcAdapter(AuthHandlerInterceptor authHandlerInterceptor) {
        this.authHandlerInterceptor = authHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(authHandlerInterceptor).addPathPatterns("/roje/cluster/user/**");
    }
}
