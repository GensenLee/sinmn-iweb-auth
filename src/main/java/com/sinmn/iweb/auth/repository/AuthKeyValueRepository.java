package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthKeyValue;

@Repository
public class AuthKeyValueRepository extends AbstractModelRepository<AuthKeyValue>{

	@ModelAutowired
	private Model<AuthKeyValue> authKeyValueModel;
	
	@Override
	protected Model<AuthKeyValue> getModel() {
		return authKeyValueModel;
	}
}
