package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthCompanyService;
import com.sinmn.iweb.auth.vo.inVO.AuthCompanyInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthCompanySearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthCompanyController {

	@Autowired
	private AuthCompanyService authCompanyService;
	
	@RequestMapping(path ="/admin/auth/instance/authCompany/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="集团列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthCompanySearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authCompanyService.list(svo));
	}
	
	
	@RequestMapping(path ="/admin/auth/instance/authCompany/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="集团列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthCompanyInVO authCompany)
			throws CommonException
	{
		return ApiResult.getSuccess( authCompanyService.save(authCompany,AuthContext.getUserInfoInnerVO()));
	}
}
