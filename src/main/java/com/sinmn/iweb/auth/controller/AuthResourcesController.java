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
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthResourcesService;
import com.sinmn.iweb.auth.vo.inVO.AuthResourcesSaveInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthResourcesSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthResourcesController {

	@Autowired
	private AuthResourcesService authResourcesService;
	
	@RequestMapping(path ="/admin/auth/instance/authResources/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="资源列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthResourcesSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authResourcesService.list(svo));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authResources/listForApp.do",method = {RequestMethod.POST})
	@AuthResource(parent="资源列表",name="列表搜索_APP")
	public ApiResult<Object> listForApp(@RequestBody AuthResourcesSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authResourcesService.listForApp(svo));
	}
	
	
	@RequestMapping(path ="/admin/auth/instance/authResources/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="资源列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthResourcesSaveInVO authResourcesSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authResourcesService.save(authResourcesSaveInVO,AuthContext.getUserInfoInnerVO()));
	}

}
