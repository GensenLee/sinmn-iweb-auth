package com.sinmn.iweb.auth.constant;

public class AuthConstant {
	
	/**
	 * @Description 通用字典
	 * @author xhz
	 * @date 2018年9月9日 下午1:04:03
	 */
	public static class Common{
		
		/**
		 * 否
		 */
		public static final int NO = 0;
		
		/**
		 * 是
		 */
		public static final int YES = 1;
		
		public static final String[] EXCLUDE_FIELDS = new String[]{"create_name","create_time","modify_name","modify_time"};
		
	}
	
	public static class KeyValueType{
		/**
		 * 系统
		 */
		public static final int SYSTEM = 0;
	}
	
	public static class KeyValueKey{
		/**
		 * 系统名字
		 */
		public static final String SYSTEM_NAME = "system_name";
		
		/**
		 * 版权信息
		 */
		public static final String COPYRIGHT = "copyright";
	}
	
	public static class ResourcesType{
		/**
		 * 链接
		 */
		public static final int LINK = 1;
		
		/**
		 * 按钮
		 */
		public static final int BUTTON = 2;
		
		/**
		 * 无
		 */
		public static final int NONE = 3;
	}
}
