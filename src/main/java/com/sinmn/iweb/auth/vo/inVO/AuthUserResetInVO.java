package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.vo.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Gensen.Lee
 * @date 2019/4/23 12:59
 */
@Data
@ApiModel("重置密码参数")
public class AuthUserResetInVO extends BaseBean {

//    public AuthUserResetInVO(String userId,String resetToken1,String resetToken2){
//        this.userId = userId;
//        this.resetToken1 = resetToken1;
//        this.resetToken2 = resetToken2;
//    }

    private String userId;
    @ApiModelProperty("随机token1")
    private String resetToken1;
    @ApiModelProperty("随机token2")
    private String resetToken2;
    @ApiModelProperty("新密码")
    private String newPasswd;
    @ApiModelProperty("重置密码时验证的key")
    private String resetKey;
}
