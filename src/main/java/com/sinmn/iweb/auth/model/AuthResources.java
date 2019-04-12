package com.sinmn.iweb.auth.model;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_resources",create=true,comment="资源")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthResources extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5787751935676608556L;

	/** 流水号 */
	public final static String ID = "id";
	
	/** 父节点id */
	public final static String PARENT_ID = "parent_id";
	
	/** 名字 */
	public final static String NAME = "name";
	
	/** 地址 */
	public final static String URL = "url";
	
	/** 系统id */
	public static final String APP_ID = "app_id";
	
	/** 类型 1接口 2按钮 */
	public final static String TYPE = "type";
	
	/** 编码 */
	public final static String CODE = "code";
	
	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true,comment="流水号")
	private Long id;
	
	@Column(name = "parent_id",jdbcType="bigint(20)",notNull=true,def="0",comment="父节点id")
	private Long parentId;
	
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="名字")
	private String name;
	
	@Column(name = "url",jdbcType="varchar(500)",notNull=true,def="''",comment="地址")
	private String url;
	
	@Column(name = "type",jdbcType="tinyint(3)",notNull=true,def="0",comment="类型 0无 1接口 2按钮")
	private Integer type;
	
	@Column(name = "code",jdbcType="varchar(100)",notNull=true,def="''",comment="编码")
	private String code;
	
	@Column(name = "app_id",jdbcType="bigint(20)",notNull=true,def="0",comment="系统id")
	private Long appId;

	public AuthResources(){
		
	}

}
