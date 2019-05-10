package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_app",create=true,comment="应用系统信息")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthApp extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 英文名字 */
	public static final String CODE = "code";
	/** 中文名字 */
	public static final String CN_NAME = "cn_name";
	/** 系统描述 */
	public static final String DESCRIPT = "descript";
	/** 系统地址 */
	public static final String URL = "url";
	/** 系统类型 0内部系统 1外部系统 */
	public static final String TYPE = "type";
	/** 扩展字段 */
	public static final String EXT = "ext";
	/** 是否可用,0不可用，1可用 */
	public static final String IS_ACTIVE = "is_active";
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
	
	@Column(name = "code",jdbcType="varchar(30)",notNull=true,def="''",comment="英文名字")
	@VerifyField(value = "英文名字")
	private String code;
	
	@Column(name = "cn_name",jdbcType="varchar(30)",notNull=true,def="''",comment="中文名字")
	@VerifyField(value = "中文名字")
	private String cnName;
	
	@Column(name = "descript",jdbcType="varchar(200)",notNull=true,def="''",comment="系统描述")
	@VerifyField(value = "系统描述")
	private String descript;
	
	@Column(name = "url",jdbcType="varchar(500)",notNull=true,def="''",comment="系统地址")
	@VerifyField(value = "系统地址",ignore = true)
	private String url;
	
	@Column(name = "type",jdbcType="tinyint(3)",notNull=true,def="0",comment="系统类型 0内部系统 1外部系统")
	@VerifyField(value = "系统类型",ignore = true)
	private Integer type;
	
	@Column(name = "is_active",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否可用,0不可用，1可用")
	@VerifyField(ignore = true)
	private Integer isActive;
	
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
	
	@SuppressWarnings("serial")
	public static List<AuthApp> init(){
		final Date now = new Date();
		return new ArrayList<AuthApp>(){{
			add(new AuthApp("inner_system","内部系统","内部系统-包括内部SUC子系统","javascript:void(0)",0,1,"system",now,"system",now));
		}};
	}
	
	public AuthApp(){
		
	}
	
	

	public AuthApp(String code, String cnName, String descript, String url, Integer type, Integer isActive,
			String createName, Date createTime, String modifyName, Date modifyTime) {
		super();
		this.code = code;
		this.cnName = cnName;
		this.descript = descript;
		this.url = url;
		this.type = type;
		this.isActive = isActive;
		this.createName = createName;
		this.createTime = createTime;
		this.modifyName = modifyName;
		this.modifyTime = modifyTime;
	}
}
