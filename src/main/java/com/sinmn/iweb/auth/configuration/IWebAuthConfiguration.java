package com.sinmn.iweb.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sinmn.core.utils.redis.configuration.EnableRedis;
import com.sinmn.iweb.auth.interceptor.GlobalAuthInterceptor;
import com.sinmn.iweb.auth.interceptor.InstanceAuthInterceptor;
import com.sinmn.iweb.auth.interceptor.UserAuthInterceptor;
import com.sinmn.iweb.auth.resource.AuthResourceAspect;

@Configuration
@ComponentScan(basePackages = {
	    "com.sinmn.iweb.auth"
	})
@EnableRedis
public class IWebAuthConfiguration extends WebMvcConfigurerAdapter{
   
	@Bean
	public AuthResourceAspect authResourceAspect(){
		return new AuthResourceAspect();
	}
	
	@Bean
	public GlobalAuthInterceptor globalAuthInterceptor(){
		return new GlobalAuthInterceptor();
	}
	
	@Bean
	public UserAuthInterceptor userAuthInterceptor(){
		return new UserAuthInterceptor();
	}
	
	@Bean
	public InstanceAuthInterceptor instanceAuthInterceptor(){
		return new InstanceAuthInterceptor();
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalAuthInterceptor())
        	.addPathPatterns("/**");
        registry.addInterceptor(userAuthInterceptor())
    	.addPathPatterns("/**/user/**");
        registry.addInterceptor(instanceAuthInterceptor())
    	.addPathPatterns("/**/instance/**");
        super.addInterceptors(registry);
    }
}
