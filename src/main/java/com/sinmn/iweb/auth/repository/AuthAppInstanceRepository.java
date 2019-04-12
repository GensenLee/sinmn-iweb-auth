package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthAppInstance;

@Repository
public class AuthAppInstanceRepository extends AbstractModelRepository<AuthAppInstance>{

	@ModelAutowired
	private Model<AuthAppInstance> authAppInstanceModel;
	
	@Override
	protected Model<AuthAppInstance> getModel() {
		return authAppInstanceModel;
	}
}
