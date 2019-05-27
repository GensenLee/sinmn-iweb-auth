package com.sinmn.iweb.auth.controller;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthUser;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthUserService;
import com.sinmn.iweb.auth.vo.inVO.AuthLoginInVO;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthUserSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AuthResource(appId = 1)
public class AuthUserController {


    @Autowired
    private AuthUserService authUserService;


    @Value("${sysUrl}")
    private String sysUrl;


    @RequestMapping(path = "/admin/auth/common/authUser/login.do", method = {RequestMethod.POST})
    public ApiResult<Object> login(@RequestBody AuthLoginInVO authLoginInVO, HttpServletRequest req)
            throws CommonException {
        return ApiResult.getSuccess("登录成功", authUserService.login(authLoginInVO, req));
    }

    @RequestMapping(path = "/admin/auth/instance/authUser/list.do", method = {RequestMethod.POST})
    @AuthResource(parent = "用户列表", name = "列表搜索")
    public ApiResult<Object> list(@RequestBody AuthUserSearchVO svo)
            throws CommonException {
        return ApiResult.getSuccess(authUserService.list(svo, AuthContext.getUserInfoInnerVO()));
    }

    @RequestMapping(path = "/admin/auth/instance/authUser/save.do", method = {RequestMethod.POST})
    @AuthResource(parent = "用户列表", name = "新增保存", type = AuthResource.BUTTON)
    public ApiResult<Object> save(@RequestBody AuthUser authUser)
            throws CommonException {
        return ApiResult.getSuccess(authUserService.save(authUser, AuthContext.getUserInfoInnerVO()));
    }

    @RequestMapping(path = "/admin/auth/instance/authApp/rePasswd.do", method = {RequestMethod.POST})
    public ApiResult<Object> rePasswd(@RequestBody AuthUserRepasswdInVO authUserRepasswdInVO)
            throws CommonException {
        return ApiResult.getSuccess(authUserService.rePasswd(authUserRepasswdInVO, AuthContext.getUserInfoInnerVO()));
    }

    @RequestMapping(path = "/admin/auth/instance/authUser/active.do", method = {RequestMethod.POST})
    @AuthResource(parent = "用户列表", name = "账号激活", type = AuthResource.BUTTON)
    public ApiResult<Object> active(@RequestBody AuthUser authUser)
            throws CommonException {
        return ApiResult.getSuccess(authUserService.active(authUser, AuthContext.getUserInfoInnerVO()));
    }


}
