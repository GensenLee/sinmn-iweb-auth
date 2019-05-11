package com.sinmn.iweb.auth.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.sinmn.core.utils.redis.configuration.EnableRedis;

@Configuration
@ComponentScan(basePackages = {
	    "com.sinmn.iweb.auth.repository",
	    "com.sinmn.iweb.auth.core",
	    "com.sinmn.iweb.auth.redis"
	})
@EnableRedis
public class IWebAuthRepositoryConfiguration{
  
}
