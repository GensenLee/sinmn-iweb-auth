package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthRole;

@Repository
public class AuthRoleRepository extends AbstractModelRepository<AuthRole>{

	@ModelAutowired
	private Model<AuthRole> authRoleModel;
	
	@Override
	protected Model<AuthRole> getModel() {
		return authRoleModel;
	}
}
