package com.sinmn.iweb.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.IntUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.MapUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthRole;
import com.sinmn.iweb.auth.model.AuthRoleMenu;
import com.sinmn.iweb.auth.repository.AuthAppInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.repository.AuthRoleMenuRepository;
import com.sinmn.iweb.auth.repository.AuthRoleRepository;
import com.sinmn.iweb.auth.vo.inVO.AuthRoleSaveInVO;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthRoleSearchVO;

@Service
public class AuthRoleService {

	@Autowired
	private AuthRoleRepository authRoleRepository;
	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	@Autowired
	private AuthAppInstanceRepository authAppInstanceRepository;
	
	@Autowired
	private AuthRoleMenuRepository authRoleMenuRepository;
	
	public Object list(AuthRoleSearchVO svo,UserInfoInnerVO userInfoInnerVO){
		
		if(LongUtil.isNotZero(svo.getAppId())){
			authRoleRepository.where(AuthRole.APP_ID,svo.getAppId());
		}else if(LongUtil.isNotZero(svo.getAppInstanceId())){
			AuthAppInstance authAppInstance = authAppInstanceRepository
				.where(AuthAppInstance.COMPANY_ID,userInfoInnerVO.getCompanyId())
				.where(AuthAppInstance.ID,svo.getAppInstanceId())
				.get();
			if(authAppInstance != null){
				authRoleRepository.where(AuthRole.APP_ID,authAppInstance.getAppId());
			}
		}
		
		List<AuthRole> liAuthRole = authRoleRepository
				.where(AuthRole.DEL_FLAG,AuthConstant.Common.NO)
				.where(AuthRole.COMPANY_ID,userInfoInnerVO.getCompanyId())
				.list();
		
		if(liAuthRole.isEmpty()){
			return null;
		}
		
		List<Map> mapResult = authAppRepository.join(liAuthRole,
				AuthRole.APP_ID,AuthApp.ID,
				AuthApp.CN_NAME,"appName",
				AuthApp.URL,"url")
				.list(Map.class);
		
		return MapUtil.toMapListResultExclude(mapResult,"isAdmin");
	}
	
	public Object listForPage(AuthRoleSearchVO svo,UserInfoInnerVO userInfoInnerVO){
		
		ModelWhere mw = new ModelWhere();
		
		mw.add(AuthRole.DEL_FLAG,AuthConstant.Common.NO);
		
		if(LongUtil.isZero(svo.getCompanyId())){
			mw.add(AuthRole.COMPANY_ID,userInfoInnerVO.getCompanyId());
		}else if(LongUtil.toLong(svo.getCompanyId()) != AuthConstant.Common.ALL){
			mw.add(AuthRole.COMPANY_ID,svo.getCompanyId());
		}
		
		
		PageResult pageResult = new PageResult();
		List<AuthRole> liAuthRole = authRoleRepository
				.where(mw).limit(svo.getStart(),svo.getSize()).list();
		
		if(liAuthRole.isEmpty()){
			return pageResult;
		}
		
		List<Map> mapResult = authAppRepository.join(liAuthRole,
				AuthRole.APP_ID,AuthApp.ID,
				AuthApp.CN_NAME,"appName",
				AuthApp.URL,"url")
				.list(Map.class);
		
		pageResult.setList(MapUtil.toMapListResultExclude(mapResult,"isAdmin"));
		pageResult.setCount(authRoleRepository
				.where(mw).count());
		return pageResult;
	}
	
	public Object save(AuthRoleSaveInVO authRoleSaveInVO,UserInfoInnerVO userInfoInnerVO)
		throws CommonException
	{
		VerifyUtil.verify(authRoleSaveInVO,
				AuthRole.APP_ID,AuthRole.IS_ADMIN);
		
		if(LongUtil.isNotZero(authRoleSaveInVO.getId())){
			BeanUtil.initModify(authRoleSaveInVO, userInfoInnerVO.getUserName());
			
			AuthRole authRole = authRoleRepository
					.where(AuthRole.APP_ID,authRoleSaveInVO.getAppId())
					.where(AuthRole.COMPANY_ID,authRoleSaveInVO.getCompanyId())
					.get(authRoleSaveInVO.getId());
			
			if(authRole == null || LongUtil.toLong(authRole.getCompanyId()) != userInfoInnerVO.getCompanyId()){
				throw new CommonException("无此角色");
			}
			
			if(authRole.getIsEdit() == AuthConstant.Common.NO){
				throw new CommonException("此角色不能被修改");
			}
			authRoleRepository.update(authRoleSaveInVO);
		}else{
			BeanUtil.initCreate(authRoleSaveInVO, userInfoInnerVO.getUserName());
			authRoleSaveInVO.setCompanyId(userInfoInnerVO.getCompanyId());
			
			authRoleRepository.insert(authRoleSaveInVO);
		}
		
		//删除对应关系
		authRoleMenuRepository
		.where(AuthRoleMenu.ROLE_ID,authRoleSaveInVO.getId())
		.delete();
		
		if(authRoleSaveInVO.getIsAdmin() == AuthConstant.Common.NO
				&& authRoleSaveInVO.getMenus() != null){
			
			List<AuthRoleMenu> liInsertMapperDao = new ArrayList<AuthRoleMenu>();
			for(AuthRoleMenu authRoleMenu : authRoleSaveInVO.getMenus()){
				authRoleMenu.setRoleId(authRoleSaveInVO.getId());
			}
			liInsertMapperDao = authRoleSaveInVO.getMenus();
			if(liInsertMapperDao.size() != 0){
				authRoleMenuRepository.insert(liInsertMapperDao);
			}
		}
		
		return null;
	}
	
	public Object singleSave(AuthRoleSaveInVO authRoleSaveInVO,UserInfoInnerVO userInfoInnerVO)
			throws CommonException
		{
			VerifyUtil.verify(authRoleSaveInVO,
					AuthRole.APP_ID,AuthRole.NAME,AuthRole.CODE);
			
			if(LongUtil.isNotZero(authRoleSaveInVO.getId())){
				BeanUtil.initModify(authRoleSaveInVO, userInfoInnerVO.getUserName());
				
				AuthRole authRole = authRoleRepository
						.where(AuthRole.APP_ID,authRoleSaveInVO.getAppId())
						.where(AuthRole.COMPANY_ID,authRoleSaveInVO.getCompanyId())
						.get(authRoleSaveInVO.getId());
				
				if(authRole == null || LongUtil.toLong(authRole.getCompanyId()) != userInfoInnerVO.getCompanyId()){
					throw new CommonException("无此角色");
				}
				
				if(authRole.getIsEdit() == AuthConstant.Common.NO){
					throw new CommonException("此角色不能被修改");
				}
				authRoleRepository.include(AuthRole.NAME).update(authRoleSaveInVO);
			}else{
				BeanUtil.initCreate(authRoleSaveInVO, userInfoInnerVO.getUserName());
				authRoleSaveInVO.setCompanyId(userInfoInnerVO.getCompanyId());
				authRoleRepository.insert(authRoleSaveInVO);
			}

			return null;
		}
	
	public Object get(Long id,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		AuthRole authRole = authRoleRepository
				.where(AuthRole.COMPANY_ID,userInfoInnerVO.getCompanyId())
				.where(AuthRole.ID,id)
				.get();
		
		if(authRole == null){
			throw new CommonException("无此角色");
		}
		
		Map<String,Object> result = authRole.toMap();
		
		if(IntUtil.toInt(authRole.getIsAdmin()) == AuthConstant.Common.NO){
			result.put("menuList", authRoleMenuRepository.where(AuthRoleMenu.ROLE_ID,authRole.getId()).list());
		}
		
		return result;
	}
	
	public Object delete(String ids,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		if(StringUtil.isEmpty(ids)){
			throw new CommonException("请传入需要删除的id");
		}
		authRoleRepository
		.where(AuthRole.COMPANY_ID,userInfoInnerVO.getCompanyId())
		.where(AuthRole.ID,ids,ModelOperator.IN)
		.where(AuthRole.IS_EDIT,AuthConstant.Common.YES)
		.update(AuthRole.DEL_FLAG,AuthConstant.Common.YES);
		return null;
	}
}
