package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.constant.ApiResultCode;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthMenuService;
import com.sinmn.iweb.auth.vo.inVO.AuthMenuSaveInVO;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

@RestController
@AuthResource(appId = 1)
public class AuthMenuController {

	@Autowired
	private AuthMenuService authMenuService;
	
	@RequestMapping(path ="/admin/auth/instance/authMenu/listUserMenu.do",method = {RequestMethod.POST})
	public ApiResult<Object> listUserMenu()
			throws CommonException
	{
		UserInfoInnerVO userInfoInnerVO = AuthContext.getUserInfoInnerVO();
		return ApiResult.getSuccess( authMenuService.listUserMenu(userInfoInnerVO.getUserId(),userInfoInnerVO.getUserInstanceId()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authMenu/list.do",method = {RequestMethod.GET})
	@AuthResource(parent="菜单列表",name="列表搜索")
	public ApiResult<Object> list(@RequestParam(value="appId",required=false) Long appId,
			@RequestParam(value="appInstanceId",required=false) Long appInstanceId)
			throws CommonException
	{
		return ApiResult.getSuccess(authMenuService.list(appId,appInstanceId));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authMenu/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="菜单列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthMenuSaveInVO authMenuSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authMenuService.save(authMenuSaveInVO,AuthContext.getUserInfoInnerVO()));
	}
}
