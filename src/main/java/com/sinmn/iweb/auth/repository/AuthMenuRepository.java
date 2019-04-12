package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthMenu;

@Repository
public class AuthMenuRepository extends AbstractModelRepository<AuthMenu>{

	@ModelAutowired
	private Model<AuthMenu> authMenuModel;
	
	@Override
	protected Model<AuthMenu> getModel() {
		return authMenuModel;
	}
}
