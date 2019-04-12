package com.sinmn.iweb.auth.repository;

import org.springframework.stereotype.Repository;

import com.sinmn.core.model.annotation.ModelAutowired;
import com.sinmn.core.model.core.AbstractModelRepository;
import com.sinmn.core.model.core.Model;
import com.sinmn.iweb.auth.model.AuthResources;

@Repository
public class AuthResourcesRepository extends AbstractModelRepository<AuthResources>{

	@ModelAutowired
	private Model<AuthResources> authResourcesModel;
	
	@Override
	protected Model<AuthResources> getModel() {
		return authResourcesModel;
	}
}
