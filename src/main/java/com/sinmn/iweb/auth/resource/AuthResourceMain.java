package com.sinmn.iweb.auth.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.spring.SpringContextUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthResources;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.repository.AuthResourcesRepository;
import com.sinmn.iweb.auth.vo.inVO.AuthResourcesSaveInVO;

@Component
@Lazy(false)
public class AuthResourceMain implements InitializingBean{
	
	@Autowired
	private AuthResourcesRepository authResourcesMapperDao;
	
	@Autowired
	private AuthAppRepository authAppMapperDao;
	
	private void initData(Map<Long,Map<String,AuthResourcesSaveInVO>> parentMapAuthResourcesInVO){
		if(parentMapAuthResourcesInVO.isEmpty()){
			return;
		}
		for(Long app_id : parentMapAuthResourcesInVO.keySet()){
			Map<String,AuthResourcesSaveInVO> mapAuthResourcesInVO = parentMapAuthResourcesInVO.get(app_id); 
			if(mapAuthResourcesInVO == null || mapAuthResourcesInVO.isEmpty()){
				continue;
			}
			List<AuthResourcesSaveInVO> insertList = new ArrayList<AuthResourcesSaveInVO>();
			List<AuthResourcesSaveInVO> updateList = new ArrayList<AuthResourcesSaveInVO>();
			List<String> keys = new ArrayList<String>();
			for(String key : mapAuthResourcesInVO.keySet()){
				keys.add(key);
				if(mapAuthResourcesInVO.get(key).getChildren() != null){
					for(AuthResourcesSaveInVO authResourcesInVO : mapAuthResourcesInVO.get(key).getChildren()){
						keys.add(authResourcesInVO.getName());
					}
				}
			}
			List<AuthResources> liAuthResources =  authResourcesMapperDao
					.where(AuthResources.APP_ID,app_id)
					.where(AuthResources.NAME,keys,ModelOperator.IN).list();
			
			for(AuthResources authResources : liAuthResources){
				if(authResources.getName().indexOf("-") == -1){
					AuthResourcesSaveInVO tmp = mapAuthResourcesInVO.get(authResources.getName());
					if(tmp == null){
						continue;
					}
					tmp.setId(authResources.getId());
				}else{
					String parentName = authResources.getName().split("-")[0];
					AuthResourcesSaveInVO tmp = mapAuthResourcesInVO.get(parentName);
					if(tmp == null || tmp.getChildren() == null || tmp.getChildren().isEmpty()){
						continue;
					}
					for(AuthResources child : tmp.getChildren()){
						if(child.getName().equals(authResources.getName())){
							child.setId(authResources.getId());
						}
					}
				}
			}
			
			for(String key : mapAuthResourcesInVO.keySet()){
				AuthResourcesSaveInVO authResourcesInVO = mapAuthResourcesInVO.get(key);
				if(LongUtil.isZero(authResourcesInVO.getId())){
					insertList.add(authResourcesInVO);
				}else{
					updateList.add(authResourcesInVO);
				}
			}
			if(!insertList.isEmpty()){
				authResourcesMapperDao.insert(insertList);
			}
			if(!updateList.isEmpty()){
				authResourcesMapperDao.update(updateList);
			}
			insertList.clear();
			updateList.clear();
			for(String key : mapAuthResourcesInVO.keySet()){
				AuthResourcesSaveInVO authResourcesInVO = mapAuthResourcesInVO.get(key);
				if(authResourcesInVO.getChildren() != null){
					for(AuthResourcesSaveInVO child : authResourcesInVO.getChildren()){
						child.setParentId(authResourcesInVO.getId());
						if(LongUtil.isZero(child.getId())){
							insertList.add(child);
						}else{
							updateList.add(child);
						}
					}
				}
			}
			
			if(!insertList.isEmpty()){
				authResourcesMapperDao.insert(insertList);
			}
			
			if(!updateList.isEmpty()){
				authResourcesMapperDao.update(updateList);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				authResourcesMapperDao.init();
				Map<Long,Map<String,AuthResourcesSaveInVO>> parentMapAuthResourcesInVO = new HashMap<Long,Map<String,AuthResourcesSaveInVO>>();
				Map<String, Object> beans = SpringContextUtil.getContext().getBeansWithAnnotation(RestController.class);
				Map<String,AuthApp> cacheAuthApp = new HashMap<String,AuthApp>();
				for(String name : beans.keySet()){
					try{
						Object bean = beans.get(name);
						Class<?> clazz = bean.getClass();
						RequestMapping parentRequestMapping = AnnotationUtils.findAnnotation(clazz,RequestMapping.class);
						AuthResource parentAuthResource = AnnotationUtils.findAnnotation(clazz,AuthResource.class);
						Method[] methods = clazz.getDeclaredMethods();
						
						for(Method method : methods){
							AuthResource authResource = AnnotationUtils.findAnnotation(method,AuthResource.class);
							if(authResource == null){
								continue;
							}
							String parentName = authResource.parent();
							if(StringUtil.isEmpty(parentName)){
								parentName = authResource.name();
							}
							String resourceName = authResource.name();
							if(resourceName.indexOf(parentName+"-") == -1){
								resourceName = parentName + "-" + resourceName;
							}
							
							RequestMapping requestMapping = AnnotationUtils.findAnnotation(method,RequestMapping.class);
							if(requestMapping == null){
								continue;
							}
							String path = requestMapping.path()[0];
							if(parentRequestMapping != null){
								path = parentRequestMapping.path()[0] + path;
							}
							long app_id = authResource.appId();
							if(app_id == -1 && parentAuthResource != null){
								app_id = parentAuthResource.appId();
							}
							if(parentAuthResource != null && StringUtil.isNotEmpty(parentAuthResource.appName())){
								AuthApp authApp = cacheAuthApp.get(parentAuthResource.appName());
								if(authApp == null){
									authApp = authAppMapperDao.where(AuthApp.NAME,parentAuthResource.appName()).get();
									cacheAuthApp.put(parentAuthResource.appName(),authApp);
								}
								if(authApp != null){
									app_id = authApp.getId();
								}
							}
							Map<String,AuthResourcesSaveInVO> mapAuthResourcesInVO = parentMapAuthResourcesInVO.get(app_id);
							if(mapAuthResourcesInVO == null){
								mapAuthResourcesInVO = new HashMap<String,AuthResourcesSaveInVO>();
								parentMapAuthResourcesInVO.put(app_id, mapAuthResourcesInVO);
							}
							AuthResourcesSaveInVO authResourcesInVO = mapAuthResourcesInVO.get(parentName);
							if(authResourcesInVO == null){
								authResourcesInVO = new AuthResourcesSaveInVO();
								authResourcesInVO.setName(parentName);
								authResourcesInVO.setParentId(0L);
								authResourcesInVO.setAppId(app_id);
								authResourcesInVO.setType(AuthConstant.ResourcesType.NONE);
								mapAuthResourcesInVO.put(parentName, authResourcesInVO);
							}
							
							AuthResourcesSaveInVO childAuthResourcesInVO = new AuthResourcesSaveInVO();
							childAuthResourcesInVO.setName(resourceName);
							childAuthResourcesInVO.setUrl(path);
							childAuthResourcesInVO.setAppId(app_id);
							childAuthResourcesInVO.setType(authResource.type());
							if(authResource.type() == AuthConstant.ResourcesType.BUTTON){
								String code = authResource.code();
								if(StringUtil.isEmpty(code)){
									code = path.replaceAll("^.*/(.*?/.*?)\\.do$", "$1").replace("/","-");
									code = StringUtil.toUUCase(code).toUpperCase();
								}
								childAuthResourcesInVO.setCode(code);
							}
							authResourcesInVO.addChildren(childAuthResourcesInVO);
							
						}
					}catch(Exception e){}
				}
				initData(parentMapAuthResourcesInVO);
			}
		}).start();
		
	}

}
