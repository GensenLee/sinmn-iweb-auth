package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthLoginLog;

@Repository
public class AuthLoginLogRepository extends AbstractModelRepository<AuthLoginLog>{

	@ModelAutowired
	private Model<AuthLoginLog> authLoginLogModel;
	
	@Override
	protected Model<AuthLoginLog> getModel() {
		return authLoginLogModel;
	}
}
