package com.sinmn.iweb.auth.model;

import java.util.Date;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_company",create=true,comment="集团")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthCompany extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 名字 */
	public static final String NAME = "name";
	/** 短名字 */
	public static final String SHORT_NAME = "short_name";
	/** 账号后缀 */
	public static final String ACCOUNT = "account";
	/** 扩展字段 */
	public static final String EXT = "ext";
	
	/** 创建人 */
	public static final String CREATE_NAME = "create_name";
	/** 创建时间 */
	public static final String CREATE_TIME = "create_time";
	/** 最后修改人 */
	public static final String MODIFY_NAME = "modify_name";
	/** 最后修改时间 */
	public static final String MODIFY_TIME = "modify_time";
	
	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true,comment="流水号")
	@VerifyField(ignore = true)
	private Long id;
	
	@Column(name = "name",jdbcType="varchar(50)",notNull=true,def="''",comment="名字")
	@VerifyField(value="名字")
	private String name;
	
	@Column(name = "short_name",jdbcType="varchar(50)",notNull=true,def="''",comment="短名字")
	@VerifyField(value="短名字")
	private String shortName;
	
	@Column(name = "account",jdbcType="varchar(50)",notNull=true,def="''",comment="账号后缀")
	@VerifyField(value="账号后缀")
	private String account;
	
	@Column(name = "ext",jdbcType="varchar(2000)",notNull=true,def="''",comment="扩展字段")
	@VerifyField(value = "扩展字段",ignore = true)
	private String ext;
	
	@Column(name = "create_name",jdbcType="varchar(50)",notNull=true,def="''",comment="创建人")
	@VerifyField(ignore = true)
	private String createName;
	
	@Column(name = "create_time",jdbcType="datetime",notNull=true,def="",comment="创建时间")
	@VerifyField(ignore = true)
	private Date createTime;
	
	@Column(name = "modify_name",jdbcType="varchar(50)",notNull=true,def="''",comment="最后修改人")
	@VerifyField(ignore = true)
	private String modifyName;
	
	@Column(name = "modify_time",jdbcType="datetime",notNull=true,def="",comment="最后修改时间")
	@VerifyField(ignore = true)
	private Date modifyTime;

	public AuthCompany(){
		
	}
	
	public AuthCompany(String name, String shortName, String account) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.account = account;
	}

}
