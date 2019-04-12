package com.sinmn.iweb.auth.resource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface AuthResource {
	
	/**
	 * 链接
	 */
	static final int LINK = 1;
	/**
	 * 按钮
	 */
	static final int BUTTON = 2;
	
	/**
	 * 无 
	 */
	static final int NONE = 3;

	/**
	 * 父资源
	 */
	String parent() default "";
	
	/**
	 * 子资源
	 */
	String name() default "";
	
	/**
	 * 系统id
	 */
	long appId() default -1L;
	
	/**
	 * 系统名字
	 */
	String appName() default "";
	
	/**
	 * 编码
	 */
	String code() default "";
	
	/**
	 * 类型 1链接 2按钮 3无
	 */
	int type() default 1;
}
