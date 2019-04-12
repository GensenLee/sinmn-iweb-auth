package com.sinmn.iweb.auth.model;

import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_company_menu",create=true,comment="集团菜单")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthCompanyMenu extends BaseBean{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
