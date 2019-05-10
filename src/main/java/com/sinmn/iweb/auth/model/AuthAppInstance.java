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

@Table(value = "auth_app_instance",create=true,comment="应用系统实例")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthAppInstance extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 流水号 */
	public static final String ID = "id";
	/** 应用id */
	public static final String APP_ID = "app_id";
	/** 英文名字 */
	public static final String CODE = "code";
	/** 扩展字段 */
	public static final String EXT = "ext";
	/** 集团id */
	public static final String COMPANY_ID = "company_id";
	/** 应用名字 */
	public static final String NAME = "name";
	/** 删除标志位 0未删除 1删除 */
	public static final String DEL_FLAG = "del_flag";
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
	
	@Column(name = "app_id",jdbcType="bigint(20)",notNull=true,def="0",comment="应用id")
	@VerifyField("应用id")
	private Long appId;
	
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团id")
	@VerifyField(ignore = true)
	private Long companyId;
	
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="应用名字")
	@VerifyField("应用名字")
	private String name;
	
	@Column(name = "code",jdbcType="varchar(30)",notNull=true,def="''",comment="英文名字")
	@VerifyField(value = "英文名字",ignore = true)
	private String code;
	
	@Column(name = "del_flag",jdbcType="tinyint(3)",notNull=true,def="0",comment="删除标志位 0未删除 1删除")
	@VerifyField(ignore = true)
	private Integer delFlag;
	
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
	public static List<AuthAppInstance> init(){
		final Date now = new Date();
		return new ArrayList<AuthAppInstance>(){{
			add(new AuthAppInstance(1L,-1L,"运营支撑系统",0,"system",now,"system",now));
		}};
	}
	
	public AuthAppInstance(Long appId, Long companyId, String name, Integer delFlag, String createName,
			Date createTime, String modifyName, Date modifyTime) {
		super();
		this.appId = appId;
		this.companyId = companyId;
		this.name = name;
		this.delFlag = delFlag;
		this.createName = createName;
		this.createTime = createTime;
		this.modifyName = modifyName;
		this.modifyTime = modifyTime;
	}
	
	public AuthAppInstance(){
		
	}
	
}
