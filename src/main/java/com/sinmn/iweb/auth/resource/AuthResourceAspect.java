package com.sinmn.iweb.auth.resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonRuntimeException;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;
import com.sinmn.iweb.auth.model.AuthMenu;
import com.sinmn.iweb.auth.model.AuthResources;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceMenuRepository;
import com.sinmn.iweb.auth.repository.AuthMenuRepository;
import com.sinmn.iweb.auth.repository.AuthResourcesRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class AuthResourceAspect {
	
	
	@Autowired
	private AuthResourcesRepository authResourcesMapperDao;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceMapperDao;
	
	@Autowired
	private AuthAppUserInstanceMenuRepository authAppUserInstanceMenuMapperDao;
	
	@Autowired
	private AuthMenuRepository authMenuMapperDao;
	
	//定义环绕通知
	@Around("execution( * *(..)) and @annotation(authResource) and @annotation(requestMapping)")
	public Object around(ProceedingJoinPoint  pjp, AuthResource authResource,RequestMapping requestMapping) throws Throwable{
				
		log.info("[AuthResourceAspect.around] 资源环绕通知aop启动......");
		
		Signature sig = pjp.getSignature();
		
		MethodSignature msig = null;
		if(!(sig instanceof MethodSignature)){
			throw new CommonRuntimeException("该注解只能用于方法");
		}
		
		msig = (MethodSignature) sig;
		
		String name = pjp.getTarget().getClass().getName()+"."+msig.getName();
		
		RequestMapping parentRequestMapping = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(),RequestMapping.class);
		
		String uri = requestMapping.path()[0];
		
		if(parentRequestMapping != null){
			uri = parentRequestMapping.path()[0] + uri;
		}
		
		log.info("[AuthResourceAspect.around] 资源环绕通知aop 方法名:{},uri:{}......",name,uri);
		
		AuthResources authResources = authResourcesMapperDao
				.where(AuthResources.URL,uri)
				.get();
		
		if(authResources == null){
			return pjp.proceed(pjp.getArgs());
		}
		
		UserInfoInnerVO userInfoInnerVO = AuthContext.getUserInfoInnerVO();
		
		//判断该角色有没有这个菜单权限
		AuthAppUserInstance authAppUserInstance = authAppUserInstanceMapperDao.get(userInfoInnerVO.getUserInstanceId());
		
		if(authAppUserInstance.getIsAdmin().equals(AuthConstant.Common.YES)){
			//如果是管理员，拥有所有的菜单
			log.info("[AuthResourceAspect.around] 资源环绕通知aop 方法名:{} UserInstanceId:{} 管理员拥有所有菜单",name,userInfoInnerVO.getUserInstanceId());
			return pjp.proceed(pjp.getArgs());

		}else{
			//获取资源对应的菜单
			
			AuthMenu authMenu = authMenuMapperDao
					.where(AuthMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
					.where(AuthMenu.IS_ACTIVE,AuthConstant.Common.YES)
					.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
					.get();
			
			
			if(authMenu == null){
				//资源没有对应的菜单
				log.info("[AuthResourceAspect.around] 资源({})没有设置对应的菜单",authResources.getId());
				return pjp.proceed(pjp.getArgs());
			}
			
			if(authAppUserInstanceMenuMapperDao
					.where(AuthAppUserInstanceMenu.APP_USER_INSTANCE_ID,authAppUserInstance.getId())
					.where(AuthAppUserInstanceMenu.RESOURCES,","+authResources.getId()+",",ModelOperator.LIKE)
					.where(AuthAppUserInstanceMenu.MENU_ID,authMenu.getId()).isNotExists()){
				//没有这个菜单权限 return false;
				throw new AuthResourcePermissionDeniedException();
			}
		}
		
		return pjp.proceed(pjp.getArgs());
	}
}
