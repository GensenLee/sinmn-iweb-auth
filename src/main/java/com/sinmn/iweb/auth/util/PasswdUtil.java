package com.sinmn.iweb.auth.util;

import com.sinmn.core.utils.util.MD5;
import com.sinmn.core.utils.util.StringUtil;

public class PasswdUtil {

	public static final String PASSWD_TEMPLATE = "%s_w67uz!@#swkd5_%s";

	public static String getPasswd(String passwd,String passwdSuffix){
		if(StringUtil.isEmpty(passwdSuffix)){
			passwdSuffix = "";
		}
		return MD5.getMD5(String.format(PASSWD_TEMPLATE, passwd,passwdSuffix));
	}
}
