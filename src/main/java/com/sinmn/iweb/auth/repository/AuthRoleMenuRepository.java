package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthRoleMenu;

@Repository
public class AuthRoleMenuRepository extends AbstractModelRepository<AuthRoleMenu>{

	@ModelAutowired
	private Model<AuthRoleMenu> authRoleMenuModel;
	
	@Override
	protected Model<AuthRoleMenu> getModel() {
		return authRoleMenuModel;
	}
}
