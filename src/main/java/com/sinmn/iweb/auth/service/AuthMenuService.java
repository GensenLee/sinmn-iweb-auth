package com.sinmn.iweb.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.ListUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.MapUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;
import com.sinmn.iweb.auth.model.AuthMenu;
import com.sinmn.iweb.auth.model.AuthResources;
import com.sinmn.iweb.auth.model.AuthRole;
import com.sinmn.iweb.auth.model.AuthRoleMenu;
import com.sinmn.iweb.auth.repository.AuthAppInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceMenuRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthMenuRepository;
import com.sinmn.iweb.auth.repository.AuthResourcesRepository;
import com.sinmn.iweb.auth.repository.AuthRoleMenuRepository;
import com.sinmn.iweb.auth.repository.AuthRoleRepository;
import com.sinmn.iweb.auth.repository.AuthUserRepository;
import com.sinmn.iweb.auth.vo.inVO.AuthMenuSaveInVO;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.outVO.AuthMenuOutVO;

@Service
public class AuthMenuService {

	@Autowired
	private AuthMenuRepository authMenuRepository;
	
	@Autowired
	private AuthAppInstanceRepository authAppInstanceRepository;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceRepository;
	
	@Autowired
	private AuthAppUserInstanceMenuRepository authAppUserInstanceMenuRepository;
	
	@Autowired
	private AuthRoleRepository authRoleRepository;
	
	@Autowired
	private AuthRoleMenuRepository authRoleMenuRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private AuthResourcesRepository authResourcesRepository;

	
	
	public Object list(Long appId,Long appInstanceId){
		if(LongUtil.isNotZero(appInstanceId)){
			AuthAppInstance authAppInstance =authAppInstanceRepository.get(appInstanceId);
			if(authAppInstance == null){
				appId = 0L;
			}else{
				appId = authAppInstance.getAppId();
			}
		}
		List<AuthMenuOutVO> liAuthMenu = authMenuRepository
				.exclude(AuthConstant.Common.EXCLUDE_FIELDS)
				.where(AuthMenu.APP_ID,appId)
				.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
				.orderBy(AuthMenu.SORT,"asc")
				.list(AuthMenuOutVO.class);
		
		Map<Long,AuthMenuOutVO> mapAuthMenu = MapUtil.toMap(liAuthMenu, AuthMenu.ID);
		List<AuthMenuOutVO> result = new ArrayList<AuthMenuOutVO>();
		for(AuthMenuOutVO authMenu : liAuthMenu){
			if(LongUtil.isZero(authMenu.getParentId())){
				result.add(authMenu);
				continue;
			}
			AuthMenuOutVO parentMenu = mapAuthMenu.get(authMenu.getParentId());
			if(parentMenu != null){
				parentMenu.addChildren(authMenu);
			}
		}
		return MapUtil.toMapListResultExclude(result,AuthConstant.Common.EXCLUDE_FIELDS);
	}
	
	public Object listUserMenu(Long userId,Long userInstanceId){
		
		//AuthUser authUser = authUserRepository.exclude(AuthConstant.Common.EXCLUDE_FIELDS).get(userId);
		
		AuthAppUserInstance authAppUserInstance = authAppUserInstanceRepository.get(userInstanceId);
		List<AuthMenu> liAuthMenu = new ArrayList<AuthMenu>();
		
		Set<String> stResourceIds = new HashSet<String>();
		
		Integer isAdmin = authAppUserInstance.getIsAdmin();
		
		if(LongUtil.isNotZero(authAppUserInstance.getRoleId())){
			//如果有角色，以角色为主
			AuthRole authRole = authRoleRepository.get(authAppUserInstance.getRoleId());
			isAdmin = (int)authRole.getIsAdmin();
		}
		
		
		
		if(isAdmin.equals(AuthConstant.Common.YES)){
			//如果是管理员，获取所有的菜单
			liAuthMenu = authMenuRepository
					.where(AuthMenu.APP_ID,authAppUserInstance.getAppId())
					.where(AuthMenu.IS_ACTIVE,AuthConstant.Common.YES)
					.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
					.orderBy(AuthMenu.SORT,"asc")
					.list();
			
			if(stResourceIds.isEmpty()){
				stResourceIds = new HashSet<String>(authResourcesRepository.include(AuthResources.ID)
						.where(AuthResources.APP_ID,authAppUserInstance.getAppId())
						.where(AuthResources.TYPE,AuthConstant.ResourcesType.BUTTON)
						.listString());
			}
			
		}else{
			List<Long> ids = new ArrayList<Long>();
			if(LongUtil.isNotZero(authAppUserInstance.getRoleId())){
				List<AuthRoleMenu> liAuthRoleMenu = authRoleMenuRepository
						.where(AuthRoleMenu.ROLE_ID,authAppUserInstance.getRoleId()).list();
				ids = ListUtil.toLongList(liAuthRoleMenu, AuthRoleMenu.MENU_ID);
				if(!ids.isEmpty()){
					for(AuthRoleMenu authRoleMenu : liAuthRoleMenu){
						if(StringUtil.isNotEmpty(authRoleMenu.getResources())){
							String resource = authRoleMenu.getResources().replaceAll("^,", "").replaceAll(",$", "");
							for(String r : resource.split(",")){
								stResourceIds.add(r);
							}
						}
					}
				}
				
			}else{
				List<AuthAppUserInstanceMenu> liAuthAppUserInstanceMenu = authAppUserInstanceMenuRepository
						.where(AuthAppUserInstanceMenu.APP_USER_INSTANCE_ID,userInstanceId).list();
				ids = ListUtil.toLongList(liAuthAppUserInstanceMenu, AuthAppUserInstanceMenu.MENU_ID);
				if(!ids.isEmpty()){
					for(AuthAppUserInstanceMenu authAppUserInstanceMenu : liAuthAppUserInstanceMenu){
						if(StringUtil.isNotEmpty(authAppUserInstanceMenu.getResources())){
							String resource = authAppUserInstanceMenu.getResources().replaceAll("^,", "").replaceAll(",$", "");
							for(String r : resource.split(",")){
								stResourceIds.add(r);
							}
						}
					}
				}
			}
			if(!ids.isEmpty()){
				liAuthMenu = authMenuRepository
						.where(AuthMenu.APP_ID,authAppUserInstance.getAppId())
						.where(AuthMenu.ID,ids,ModelOperator.IN)
						.where(AuthMenu.IS_ACTIVE,AuthConstant.Common.YES)
						.where(AuthMenu.DEL_FLAG,AuthConstant.Common.NO)
						.orderBy(AuthMenu.SORT,"asc")
						.list();
			}
		}
		
		List<AuthMenuOutVO> liAuthMenuOutVO = BeanUtil.copy(liAuthMenu,AuthMenuOutVO.class);
		Map<Long,AuthMenuOutVO> mapAuthMenu = MapUtil.toMap(liAuthMenuOutVO, AuthMenu.ID);
		List<AuthMenuOutVO> result = new ArrayList<AuthMenuOutVO>();
		
		
		for(AuthMenuOutVO authMenu : liAuthMenuOutVO){
			if(LongUtil.isZero(authMenu.getParentId())){
				result.add(authMenu);
				continue;
			}
			AuthMenuOutVO parentMenu = mapAuthMenu.get(authMenu.getParentId());
			if(parentMenu != null){
				parentMenu.addChildren(authMenu);
			}
			
		}
		List<String> liResourceIds = new ArrayList<String>(stResourceIds);
		List<String> liCode = new ArrayList<String>();
		if(!liResourceIds.isEmpty()){
			liCode = authResourcesRepository.include(AuthResources.CODE)
			.where(AuthResources.ID,liResourceIds,ModelOperator.IN)
			.where(AuthResources.TYPE,AuthConstant.ResourcesType.BUTTON)
			.listString();
		}
		
		Map<String,Object> mapResult = new HashMap<String,Object>();
		mapResult.put("menus", MapUtil.toMapListResultExclude(result,AuthConstant.Common.EXCLUDE_FIELDS));
		mapResult.put("auth", liCode);
		return mapResult;
		
	}
	
	private void getMenu(List<AuthMenuSaveInVO> out,AuthMenuSaveInVO authMenuSaveInVO){
		out.add(authMenuSaveInVO);
		if(authMenuSaveInVO.getChildren() == null || authMenuSaveInVO.getChildren().isEmpty()){
			return;
		}
		for(AuthMenuSaveInVO authMenu : authMenuSaveInVO.getChildren()){
			getMenu(out,authMenu);
		}
	}
	
	private void save(List<AuthMenuSaveInVO> liAuthMenuSaveInVO,Long parentId,String path,UserInfoInnerVO userInfoInnerVO){
		if(liAuthMenuSaveInVO == null || liAuthMenuSaveInVO.isEmpty()){
			return;
		}
		List<AuthMenuSaveInVO> liUpdate = new ArrayList<AuthMenuSaveInVO>();
		List<AuthMenuSaveInVO> liInsert = new ArrayList<AuthMenuSaveInVO>();
		
		for(AuthMenuSaveInVO authMenuSaveInVO : liAuthMenuSaveInVO){
			authMenuSaveInVO.setPath(path+parentId+",");
			authMenuSaveInVO.setParentId(parentId);
			authMenuSaveInVO.setDelFlag(AuthConstant.Common.NO);
			if(LongUtil.isNotZero(authMenuSaveInVO.getId())){
				BeanUtil.initModify(authMenuSaveInVO, userInfoInnerVO.getUserName());
				liUpdate.add(authMenuSaveInVO);
			}else{
				BeanUtil.initCreate(authMenuSaveInVO, userInfoInnerVO.getUserName());
				liInsert.add(authMenuSaveInVO);
			}
		}
		if(!liUpdate.isEmpty()){
			authMenuRepository.update(liUpdate);
		}
		if(!liInsert.isEmpty()){
			authMenuRepository.insert(liInsert);
		}
		liInsert.addAll(liUpdate);
		liUpdate.clear();
		for(AuthMenuSaveInVO authMenuSaveInVO : liInsert){
			save(authMenuSaveInVO.getChildren(),authMenuSaveInVO.getId(),authMenuSaveInVO.getPath(),userInfoInnerVO);
		}
	}
	public Object save(AuthMenuSaveInVO authMenuSaveInVO,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		if(authMenuSaveInVO == null){
			throw new CommonException("请传入正确的参数");
		}
		authMenuRepository
		.where(AuthMenu.APP_ID,authMenuSaveInVO.getAppId())
		.update(AuthMenu.DEL_FLAG, AuthConstant.Common.YES);
		
		if(authMenuSaveInVO.getChildren() == null || authMenuSaveInVO.getChildren().isEmpty()){
			return null;
		}
		
		save(authMenuSaveInVO.getChildren(),0L,",",userInfoInnerVO);
		
		return null;
	}
}
