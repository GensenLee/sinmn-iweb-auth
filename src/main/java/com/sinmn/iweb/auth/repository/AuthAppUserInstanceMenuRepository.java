package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;

@Repository
public class AuthAppUserInstanceMenuRepository extends AbstractModelRepository<AuthAppUserInstanceMenu>{

	@ModelAutowired
	private Model<AuthAppUserInstanceMenu> authAppUserInstanceMenuModel;
	
	@Override
	protected Model<AuthAppUserInstanceMenu> getModel() {
		return authAppUserInstanceMenuModel;
	}
}
