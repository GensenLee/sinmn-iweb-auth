package com.sinmn.iweb.auth.main;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.Model;
import com.sinmn.core.model.main.AbstractModelMain;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;
import com.sinmn.iweb.auth.model.AuthCompany;
import com.sinmn.iweb.auth.model.AuthKeyValue;
import com.sinmn.iweb.auth.model.AuthLoginLog;
import com.sinmn.iweb.auth.model.AuthMenu;
import com.sinmn.iweb.auth.model.AuthUser;

import lombok.Data;

@Component
@Lazy(false)
@Data
public class AuthInitMain extends AbstractModelMain{

	@ModelAutowired
	private Model<AuthApp> authAppModel;
	
	@ModelAutowired
	private Model<AuthAppInstance> authAppInstanceModel;
	
	@ModelAutowired
	private Model<AuthAppUserInstance> authAppUserInstanceModel;
	
	@ModelAutowired
	private Model<AuthAppUserInstanceMenu> authAppUserInstanceMenuModel;
	
	@ModelAutowired
	private Model<AuthMenu> authMenuModel;
	
	@ModelAutowired
	private Model<AuthUser> authUserModel;
	
	@ModelAutowired
	private Model<AuthCompany> authCompanyModel;
	
	@ModelAutowired
	private Model<AuthKeyValue> authKeyValueModel;
	
	@ModelAutowired
	private Model<AuthLoginLog> authLoginLogModel;
	
}
