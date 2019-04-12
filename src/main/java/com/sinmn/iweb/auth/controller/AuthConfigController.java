package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.constant.ApiResultCode;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthKeyValue;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthConfigService;
import com.sinmn.iweb.auth.vo.searchVO.AuthConfigSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthConfigController {

	@Autowired
	private AuthConfigService authConfigService;
	
	@RequestMapping(path ="/admin/auth/instance/authConfig/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="参数列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthConfigSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess( authConfigService.list(svo));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authConfig/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="参数列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthKeyValue authKeyValue)
			throws CommonException
	{
		return ApiResult.getSuccess( authConfigService.save(authKeyValue,AuthContext.getUserInfoInnerVO()));
	}
}
