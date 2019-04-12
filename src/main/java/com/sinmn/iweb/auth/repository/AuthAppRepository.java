package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthApp;

@Repository
public class AuthAppRepository extends AbstractModelRepository<AuthApp>{

	@ModelAutowired
	private Model<AuthApp> authAppModel;
	
	@Override
	protected Model<AuthApp> getModel() {
		return authAppModel;
	}
}
