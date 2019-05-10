package com.sinmn.iweb.auth.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.IPUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.MapUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthCompany;
import com.sinmn.iweb.auth.model.AuthLoginLog;
import com.sinmn.iweb.auth.model.AuthRole;
import com.sinmn.iweb.auth.model.AuthUser;
import com.sinmn.iweb.auth.redis.IWebAuthRedisDao;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthCompanyRepository;
import com.sinmn.iweb.auth.repository.AuthLoginLogRepository;
import com.sinmn.iweb.auth.repository.AuthUserRepository;
import com.sinmn.iweb.auth.util.PasswdUtil;
import com.sinmn.iweb.auth.vo.inVO.AuthLoginInVO;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthUserSearchVO;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AuthUserService {

	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceRepository;
	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	@Autowired
	private AuthCompanyRepository authCompanyRepository;
	
	@Autowired
	private IWebAuthRedisDao iWebAuthRedisDao;
	
	@Autowired
	private AuthLoginLogRepository authLoginLogRepository;
	
	
	
	private static String targetIp;
	
	static {
		try {
			InetAddress addr = InetAddress.getLocalHost();  
			targetIp = addr.getHostAddress().toString(); //获取本机ip  
		} catch (Exception e) {
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object list(AuthUserSearchVO svo,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		ModelWhere mw = new ModelWhere();
		
		if(StringUtil.isNotEmpty(svo.getQuickSearch())){
			mw.add(AuthUser.NAME,svo.getQuickSearch(),ModelOperator.LIKE);
		}
		
		if(LongUtil.isZero(svo.getCompanyId())){
			mw.add(AuthUser.COMPANY_ID,userInfoInnerVO.getCompanyId());
		}else if(LongUtil.toLong(svo.getCompanyId()) != AuthConstant.Common.ALL){
			mw.add(AuthUser.COMPANY_ID,svo.getCompanyId());
		}
		
		List<AuthUser> liAuthUser = authUserRepository.where(mw)
				.orderBy(AuthUser.ID,"DESC")
				.limit(svo.getStart(),svo.getSize()).list();
		
		List<Map<String,Object>> listMap = MapUtil.toMapListResultExclude(liAuthUser,AuthUser.PASSWD);
		PageResult pr = PageResult.get();
		pr.setList(listMap);
		pr.setCount(authUserRepository.where(mw).count());
		
		return pr;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object login(AuthLoginInVO authLoginInVO,HttpServletRequest req) throws CommonException{
		
		//验证
		VerifyUtil.verify(authLoginInVO);
		
		//登录成功,插入登录日志
		AuthLoginLog authLoginLog = new AuthLoginLog();
		
		AuthUser authUser = authUserRepository
				.where(AuthUser.ACCOUNT,authLoginInVO.getAccount())
				.or(AuthUser.EMAIL,authLoginInVO.getAccount())
				.get();
		
		if(authUser == null){
			throw new CommonException("账号错误");
		}
		
		if(authUser.getIsActive() == AuthConstant.Common.NO){
			throw new CommonException("账号已经被冻结，请联系管理员激活账号");
		}
		
		if(authUser.getTryCount() >= 6){
			throw new CommonException("密码输入错误次数过多，账号已经被封停，请联系系统管理人员解封");
		}
		
		authLoginLog.setCompanyId(authUser.getCompanyId());
		authLoginLog.setCreateTime(new Date());
		authLoginLog.setStatus(AuthConstant.Common.NO);
		authLoginLog.setTargetIp(targetIp);
		authLoginLog.setUserId(authUser.getId());
		authLoginLog.setIp(IPUtil.getIpAddr(req));
		authLoginLog.setAccount(authLoginInVO.getAccount());
		authLoginLog.setAddress(IPUtil.getIpRealAddr(authLoginLog.getIp()));
		
		if(!PasswdUtil.getPasswd(authLoginInVO.getPasswd(), authUser.getPasswdSuffix()).equals(authUser.getPasswd())){
			authUser.setTryCount(authUser.getTryCount() + 1);
			if(authUser.getTryCount() >= 6){
				authUser.setIsActive(AuthConstant.Common.NO);
			}
			authUserRepository.include(AuthUser.TRY_COUNT,AuthUser.IS_ACTIVE).update(authUser);
			String message = "";
			if(authUser.getTryCount() > 2){
				message = String.format("输入错误 %d 次,你还有 %d 次机会",authUser.getTryCount(), 6-authUser.getTryCount());
			}
			authLoginLog.setReason("密码错误:"+authLoginInVO.getPasswd());
			authLoginLogRepository.insert(authLoginLog);
			throw new CommonException("密码错误"+message);
		}
		authUser.setTryCount(0);
		authUserRepository.include(AuthUser.TRY_COUNT).update(authUser);
		
		String sessionKey = UUID.randomUUID().toString().replaceAll("-", "");
		
		UserInfoInnerVO userInfoInnerVO = new UserInfoInnerVO();
		
		userInfoInnerVO.setSessionKey(sessionKey);
		userInfoInnerVO.setUserId(authUser.getId());
		userInfoInnerVO.setCompanyId(authUser.getCompanyId());
		userInfoInnerVO.setUserName(authUser.getName());
		
		//判断用户是否只有一个实例，如果只有一个实例，直接跳转
		List<AuthAppUserInstance> liAuthAppUserInstance = authAppUserInstanceRepository
				.where(AuthAppUserInstance.DEL_FLAG,AuthConstant.Common.NO)
				.where(AuthAppUserInstance.USER_ID,authUser.getId())
				.list();
		Map mapResult = new HashMap<String,Object>();
		if(liAuthAppUserInstance.size() == 1){
			mapResult = authAppRepository.join(liAuthAppUserInstance.get(0),
					AuthAppUserInstance.APP_ID,AuthAppInstance.ID,
					AuthApp.CN_NAME,"appName",
					AuthApp.URL,"url")
					.get(Map.class);
			userInfoInnerVO.setUserInstanceId(liAuthAppUserInstance.get(0).getId());
		}
		
		//登陆
		iWebAuthRedisDao.set(userInfoInnerVO);

		mapResult.put("userId", authUser.getId());
		mapResult.put("userName", authUser.getName());
		mapResult.put("sessionKey", sessionKey);
		mapResult.put("toggle", liAuthAppUserInstance.size() > 1);
		
		authLoginLog.setStatus(AuthConstant.Common.YES);
		authLoginLogRepository.insert(authLoginLog);
		
		
		return mapResult;
	}
	
	public Object save(AuthUser authUser,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		VerifyUtil.verify(authUser);
		authUser.setCompanyId(userInfoInnerVO.getCompanyId());
		authUser.setIsAdmin(AuthConstant.Common.NO);
		if(LongUtil.isNotZero(authUser.getId())){
			AuthUser orgAuthUser = authUserRepository
				.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
				.where(AuthUser.ID,authUser.getId()).get();
			if(orgAuthUser == null || orgAuthUser.getIsAdmin() == AuthConstant.Common.YES){
				throw new CommonException("无此用户权限");
			}
			authUser.setPasswd(null);
			if(authUserRepository
					.where(AuthUser.ORG_ACCOUNT,authUser.getOrgAccount())
					.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
					.where(AuthUser.ID,authUser.getId(),ModelOperator.NEQ).isExists()){
				throw new CommonException("账号已经被占用");
			}
			if(authUserRepository
					.where(AuthUser.EMAIL,authUser.getEmail())
					.where(AuthUser.ID,authUser.getId(),ModelOperator.NEQ).isExists()){
				throw new CommonException("邮箱已经被占用");
			}
			if(authUserRepository
					.where(AuthUser.NAME,authUser.getName())
					.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
					.where(AuthUser.ID,authUser.getId(),ModelOperator.NEQ).isExists()){
				throw new CommonException("名字已经被占用");
			}
			BeanUtil.initModify(authUser, userInfoInnerVO.getUserName());
		}else{
			if(authUserRepository
					.where(AuthUser.ORG_ACCOUNT,authUser.getOrgAccount())
					.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
					.isExists()){
				throw new CommonException("账号已经被占用");
			}
			if(authUserRepository
					.where(AuthUser.EMAIL,authUser.getEmail())
					.isExists()){
				throw new CommonException("邮箱已经被占用");
			}
			if(authUserRepository
					.where(AuthUser.NAME,authUser.getName())
					.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
					.isExists()){
				throw new CommonException("名字已经被占用");
			}
			BeanUtil.initCreate(authUser, userInfoInnerVO.getUserName());
		}
		
		if(StringUtil.isNotEmpty(authUser.getPasswd())){
			authUser.setPasswdSuffix(UUID.randomUUID().toString().replaceAll("-", ""));
			authUser.setPasswd(PasswdUtil.getPasswd(authUser.getPasswd(), authUser.getPasswdSuffix()));
		}
		
		if(authUser.getCompanyId() == -1){
			AuthUser admin = authUserRepository.where(AuthUser.COMPANY_ID,-1).where(AuthUser.IS_ADMIN,AuthConstant.Common.YES).get();
			String account = "admin";
			if(admin != null){
				account = admin.getOrgAccount();
			}
			authUser.setAccount(authUser.getOrgAccount()+"@"+account);
		}else{
			AuthCompany authCompany = authCompanyRepository.where(AuthCompany.ID,authUser.getCompanyId()).get();
			if(authCompany != null && StringUtil.isNotEmpty(authCompany.getAccount())){
				authUser.setAccount(authUser.getOrgAccount()+"@"+authCompany.getAccount());
			}else{
				authUser.setAccount(authUser.getOrgAccount()+"@"+authUser.getCompanyId());
			}
		}
		
		authUser.setIsAdmin(AuthConstant.Common.NO);
		authUserRepository.save(authUser);
		
		return authUser;
	}
	
	public Object rePasswd(AuthUserRepasswdInVO authUserRepasswdInVO,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		//验证
		VerifyUtil.verify(authUserRepasswdInVO);
		
		AuthUser authUser = authUserRepository.get(userInfoInnerVO.getUserId());
		
		if(authUser.getTryCount() >= 6){
			throw new CommonException("密码输入错误次数过多，账号已经被封停，请联系系统管理人员解封");
		}
		
		if(authUser.getIsActive() == AuthConstant.Common.NO){
			throw new CommonException("账号已经被冻结，请联系管理员激活账号");
		}
		
		if(!authUser.getPasswd().equals(PasswdUtil.getPasswd(authUserRepasswdInVO.getPasswd(), authUser.getPasswdSuffix()))){
			authUser.setTryCount(authUser.getTryCount() + 1);
			authUserRepository.include(AuthUser.TRY_COUNT).update(authUser);
			String message = "";
			if(authUser.getTryCount() > 2){
				message = String.format("输入错误 %d 次,你还有 %d 次机会",authUser.getTryCount(), 6-authUser.getTryCount());
			}
			throw new CommonException("原始密码错误 "+message);
		}
		
		authUser.setPasswdSuffix(UUID.randomUUID().toString().replaceAll("-", ""));
		authUser.setPasswd(PasswdUtil.getPasswd(authUserRepasswdInVO.getNewPasswd(), authUser.getPasswdSuffix()));
		authUser.setTryCount(0);
		authUserRepository.include(AuthUser.TRY_COUNT,AuthUser.PASSWD,AuthUser.PASSWD_SUFFIX).update(authUser);
		return null;
	}
	
	
	public Object active(AuthUser authUser,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		VerifyUtil.verify(authUser,AuthUser.ID,AuthUser.IS_ACTIVE);
		AuthUser orgAuthUser = authUserRepository
				.where(AuthUser.COMPANY_ID,authUser.getCompanyId())
				.where(AuthUser.ID,authUser.getId()).get();
		if(orgAuthUser == null || orgAuthUser.getIsAdmin() == AuthConstant.Common.YES){
			throw new CommonException("无此用户权限");
		}
		authUser.setTryCount(0);
		authUserRepository
		.include(AuthUser.IS_ACTIVE,AuthUser.TRY_COUNT)
		.save(authUser);
		return authUser;
	}
}
