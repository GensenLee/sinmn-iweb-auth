package com.sinmn.iweb.auth.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
	    "com.sinmn.iweb.auth.repository",
	    "com.sinmn.iweb.auth.core"
	})
public class IWebAuthRepositoryConfiguration{
  
}
