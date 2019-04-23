package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.vo.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 * @author Gensen.Lee
 * @date 2019/4/23 12:59
 */
@Data
public class AuthUserResetInVO extends BaseBean {

    public AuthUserResetInVO(String userId,String resetToken1,String resetToken2){
        this.userId = userId;
        this.resetToken1 = resetToken1;
        this.resetToken2 = resetToken2;
    }

    private String userId;
    //随机token
    private String resetToken1;
    private String resetToken2;
    private String newPasswd;
}
