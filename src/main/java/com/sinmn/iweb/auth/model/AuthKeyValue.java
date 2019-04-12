package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;
import com.sinmn.iweb.auth.constant.AuthConstant;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_key_value",create=true,comment="键值对")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthKeyValue extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 集团id */
	public static final String COMPANY_ID = "company_id";
	/** 类型 */
	public static final String TYPE = "type";
	/** 名字 */
	public static final String NAME = "name";
	/** 键 */
	public static final String KEY = "key";
	/** 值 */
	public static final String VALUE = "value";
	
	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true,comment="流水号")
	@VerifyField(ignore = true)
	private Long id;
	
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团id")
	private Long companyId;
	
	@Column(name = "type",jdbcType="tinyint(3)",notNull=true,def="0",comment="类型")
	private Integer type;
	
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="名字")
	private String name;
	
	@Column(name = "key",jdbcType="varchar(100)",notNull=true,def="''",comment="键")
	@VerifyField(value="键")
	private String key;
	
	@Column(name = "value",jdbcType="varchar(1000)",notNull=true,def="''",comment="值")
	@VerifyField(value="值")
	private String value;
	
	@SuppressWarnings("serial")
	public static List<AuthKeyValue> init(){
		return new ArrayList<AuthKeyValue>(){{
			add(new AuthKeyValue(0L,AuthConstant.KeyValueType.SYSTEM,"系统名字",AuthConstant.KeyValueKey.SYSTEM_NAME,"管理系统"));
			add(new AuthKeyValue(0L,AuthConstant.KeyValueType.SYSTEM,"版权信息",AuthConstant.KeyValueKey.COPYRIGHT,"Copyright 2018新月科技. All Rights Reserved."));
		}};
	}
	public AuthKeyValue(){
		
	}

	public AuthKeyValue(Long companyId, Integer type, String name,String key, String value) {
		super();
		this.companyId = companyId;
		this.type = type;
		this.key = key;
		this.value = value;
		this.name = name;
	}
}

