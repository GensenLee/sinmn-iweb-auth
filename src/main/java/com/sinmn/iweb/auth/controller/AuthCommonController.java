package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.constant.ApiResultCode;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthCommonService;

@RestController
@AuthResource(appId = 1)
public class AuthCommonController {

	@Autowired
	private AuthCommonService authCommonService;
	
	@RequestMapping(path ="/admin/auth/common/authCommon/get.do")
	public ApiResult<Object> getForCommon()
			throws CommonException
	{
		return ApiResult.getSuccess(authCommonService.getForCommon());
	}
	
	@RequestMapping(path ="/admin/auth/user/authCommon/get.do")
	public ApiResult<Object> getForUser()
			throws CommonException
	{
		return ApiResult.getSuccess(authCommonService.getForUser());
	}
	
	@RequestMapping(path ="/admin/auth/instance/authCommon/get.do")
	public ApiResult<Object> getForInstance()
			throws CommonException
	{
		
		return ApiResult.getSuccess( authCommonService.getForInstance(AuthContext.getUserInfoInnerVO()));
	}
}
