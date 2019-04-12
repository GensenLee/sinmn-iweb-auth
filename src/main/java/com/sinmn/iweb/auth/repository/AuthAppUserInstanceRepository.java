package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;

@Repository
public class AuthAppUserInstanceRepository extends AbstractModelRepository<AuthAppUserInstance>{

	@ModelAutowired
	private Model<AuthAppUserInstance> authAppUserInstanceModel;
	
	@Override
	protected Model<AuthAppUserInstance> getModel() {
		return authAppUserInstanceModel;
	}
}
