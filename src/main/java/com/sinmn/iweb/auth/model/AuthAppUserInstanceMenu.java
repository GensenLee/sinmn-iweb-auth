package com.sinmn.iweb.auth.model;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_app_user_instance_menu",create=true,comment="用户应用实例菜单")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthAppUserInstanceMenu extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户应用实例id
	 */
	public static final String APP_USER_INSTANCE_ID = "app_user_instance_id";
	
	/**
	 * 菜单id
	 */
	public static final String MENU_ID = "menu_id";
	
	/**
	 * 资源，前后都有逗号
	 */
	public static final String RESOURCES = "resources";
	
	@Column(name = "app_user_instance_id",jdbcType="bigint(20)",notNull=true,def="0",comment="用户应用实例id")
	private Long appUserInstanceId;
	
	@Column(name = "menu_id",jdbcType="bigint(20)",notNull=true,def="0",comment="菜单id")
	private Long menuId;
	
	@Column(name = "resources",jdbcType="varchar(500)",notNull=true,def="''",comment="资源，前后都有逗号")
	private String resources;
}
