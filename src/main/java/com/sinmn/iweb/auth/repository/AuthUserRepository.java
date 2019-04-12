package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthUser;

@Repository
public class AuthUserRepository extends AbstractModelRepository<AuthUser>{

	@ModelAutowired
	private Model<AuthUser> authUserModel;
	
	@Override
	protected Model<AuthUser> getModel() {
		return authUserModel;
	}
}
