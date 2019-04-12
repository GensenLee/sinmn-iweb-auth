package com.sinmn.iweb.auth.resource;

import com.sinmn.core.utils.constant.ApiResultCode;
import com.sinmn.core.utils.exception.CommonRuntimeException;

public class AuthResourcePermissionDeniedException extends CommonRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthResourcePermissionDeniedException(){
		super(ApiResultCode.PERMISSION_DENIED);
	}
}
