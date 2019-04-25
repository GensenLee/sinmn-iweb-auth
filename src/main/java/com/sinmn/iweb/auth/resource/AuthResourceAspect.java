package com.sinmn.iweb.auth.resource;

import java.lang.reflect.AnnotatedElement;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonRuntimeException;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;
import com.sinmn.iweb.auth.model.AuthMenu;
import com.sinmn.iweb.auth.model.AuthResources;
import com.sinmn.iweb.auth.model.AuthRole;
import com.sinmn.iweb.auth.model.AuthRoleMenu;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceMenuRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthMenuRepository;
import com.sinmn.iweb.auth.repository.AuthResourcesRepository;
import com.sinmn.iweb.auth.repository.AuthRoleMenuRepository;
import com.sinmn.iweb.auth.repository.AuthRoleRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class AuthResourceAspect {
	
	
	@Autowired
	private AuthResourcesRepository authResourcesRepository;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceRepository;
	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	@Autowired
	private AuthAppUserInstanceMenuRepository authAppUserInstanceMenuRepository;
	
	@Autowired
	private AuthRoleRepository authRoleRepository;
	
	@Autowired
	private AuthRoleMenuRepository authRoleMenuRepository;
	
	@Autowired
	private AuthMenuRepository authMenuRepository;
	
	private String getUri(AnnotatedElement annotatedElement){
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(annotatedElement,RequestMapping.class);
		String uri = "";
		
		if(requestMapping == null || requestMapping.path().length == 0){
			PostMapping postMapping = AnnotationUtils.findAnnotation(annotatedElement,PostMapping.class);
			if(postMapping == null || postMapping.path().length == 0){
				GetMapping getMapping = AnnotationUtils.findAnnotation(annotatedElement,GetMapping.class);
				if(getMapping != null && getMapping.path().length != 0){
					uri = postMapping.path()[0];
				}
			}else{
				uri = postMapping.path()[0];
			}
		}else{
			uri = requestMapping.path()[0];
		}
		
		return uri;
	}
	
	private Long getAppId(ProceedingJoinPoint pjp){
		AuthResource authResource = AnnotationUtils.findAnnotation(((MethodSignature)pjp.getSignature()).getMethod(),AuthResource.class);
		if(authResource.appId() > 0){
			return authResource.appId();
		}
		AuthResource parentAuthResource = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(),AuthResource.class);
		
		long appId = authResource.appId();
		if(appId <= 0 && parentAuthResource != null){
			appId = parentAuthResource.appId();
		}
		if(parentAuthResource != null && StringUtil.isNotEmpty(parentAuthResource.appCode())){
			AuthApp authApp = authAppRepository.where(AuthApp.CODE,parentAuthResource.appCode()).get();
			
			if(authApp != null){
				appId = authApp.getId();
			}
		}
		
		return appId;
	}
	
	//定义环绕通知
	@Around("execution( * *(..)) and @annotation(authResource)")
	public Object around(ProceedingJoinPoint  pjp, AuthResource authResource) throws Throwable{
				
		log.info("[AuthResourceAspect.around] 资源环绕通知aop启动......");
		
		Signature sig = pjp.getSignature();
		
		MethodSignature msig = null;
		if(!(sig instanceof MethodSignature)){
			throw new CommonRuntimeException("该注解只能用于方法");
		}
		
		msig = (MethodSignature) sig;
		
		String uri = getUri(msig.getMethod());
		
		log.info("[AuthResourceAspect.around] uri:{}",uri);
		
		if(StringUtil.isEmpty(uri)){
			return pjp.proceed(pjp.getArgs());
		}
		
		String name = pjp.getTarget().getClass().getName()+"."+msig.getName();
		
		String parentURI = getUri(pjp.getTarget().getClass());
		
		if(StringUtil.isNotEmpty(parentURI)){
			uri = parentURI + uri;
		}
		
		log.info("[AuthResourceAspect.around] 资源环绕通知aop 方法名:{},uri:{}......",name,uri);
		
		AuthResources authResources = authResourcesRepository
				.where(AuthResources.URL,uri)
				.get();
		
		if(authResources == null){
			return pjp.proceed(pjp.getArgs());
		}
		
		
		UserInfoInnerVO userInfoInnerVO = AuthContext.getUserInfoInnerVO();
		
		
		if(userInfoInnerVO != null && LongUtil.isNotZero(userInfoInnerVO.getUserInstanceId())){
			//判断该角色有没有这个菜单权限
			AuthAppUserInstance authAppUserInstance = authAppUserInstanceRepository.get(userInfoInnerVO.getUserInstanceId());
			
			if(authAppUserInstance == null){
				log.info("[AuthResourceAspect.around] 用户实例为空");
				throw new AuthResourcePermissionDeniedException();
			}
			
			AuthResource parentAuthResource = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(), AuthResource.class);
			
			if(authResource.ignoreApp() || (parentAuthResource != null && parentAuthResource.ignoreApp())){
				log.info("[AuthResourceAspect.around] 接口支持跨app访问");
			}else{
				//不允许跨APP调用，获取APPID
				log.info("[AuthResourceAspect.around] 接口不支持跨app访问");
				long appId = getAppId(pjp);
				log.info("[AuthResourceAspect.around] appId:{} appId:{}",appId,authAppUserInstance.getAppId());
				if(appId > 0 && appId != authAppUserInstance.getAppId()){
					throw new AuthResourcePermissionDeniedException();
				}
			}
			
			if(LongUtil.toLong(authAppUserInstance.getRoleId()) <= 0){
				//用户实例
				if(authAppUserInstance.getIsAdmin().equals(AuthConstant.Common.YES)){
					//如果是管理员，拥有所有的菜单
					log.info("[AuthResourceAspect.around] 资源环绕通知aop 方法名:{} UserInstanceId:{} 管理员拥有所有菜单",name,userInfoInnerVO.getUserInstanceId());
					return pjp.proceed(pjp.getArgs());
		
				}else{
					//获取资源对应的菜单
					
					AuthMenu authMenu = authMenuRepository
							.where(AuthMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
							.where(AuthMenu.IS_ACTIVE,AuthConstant.Common.YES)
							.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
							.get();
					
					
					if(authMenu == null){
						//资源没有对应的菜单
						log.info("[AuthResourceAspect.around] 资源({})没有设置对应的菜单",authResources.getId());
						return pjp.proceed(pjp.getArgs());
					}
					//如果是用户实例
					
					if(authAppUserInstanceMenuRepository
							.where(AuthAppUserInstanceMenu.APP_USER_INSTANCE_ID,authAppUserInstance.getId())
							.where(AuthAppUserInstanceMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
							.where(AuthAppUserInstanceMenu.MENU_ID,authMenu.getId()).isNotExists()){
						//没有这个菜单权限 return false;
						throw new AuthResourcePermissionDeniedException();
					}
					
				}
			}else{
				//角色实例
				AuthRole authRole = authRoleRepository.get(authAppUserInstance.getRoleId());
				if(authRole == null){
					//没有这个菜单权限 return false;
					throw new AuthResourcePermissionDeniedException();
				}
				
				if(authRole.getIsAdmin().equals(AuthConstant.Common.YES)){
					//如果是管理员，拥有所有的菜单
					log.info("[AuthResourceAspect.around] 资源环绕通知aop 方法名:{} UserInstanceId:{} 管理员拥有所有菜单",name,userInfoInnerVO.getUserInstanceId());
					return pjp.proceed(pjp.getArgs());
		
				}else{
					//获取资源对应的菜单
					AuthMenu authMenu = authMenuRepository
							.where(AuthMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
							.where(AuthMenu.IS_ACTIVE,AuthConstant.Common.YES)
							.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
							.get();
					
					
					if(authMenu == null){
						//资源没有对应的菜单
						log.info("[AuthResourceAspect.around] 资源({})没有设置对应的菜单",authResources.getId());
						return pjp.proceed(pjp.getArgs());
					}
					//如果是用户实例
					
					if(authRoleMenuRepository
							.where(AuthRoleMenu.ROLE_ID,authAppUserInstance.getRoleId())
							.where(AuthRoleMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
							.where(AuthRoleMenu.MENU_ID,authMenu.getId()).isNotExists()){
						//没有这个菜单权限 return false;
						throw new AuthResourcePermissionDeniedException();
					}
					
				}
				
			}
		}
		
		return pjp.proceed(pjp.getArgs());
	}
}
