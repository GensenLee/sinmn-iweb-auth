package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;
import lombok.Data;

/**
 * @author Gensen.Lee
 * @date 2019/4/26 10:40
 */
@Data
public class AuthUserRenewInVO extends BaseBean {

    @VerifyField("原始密码")
    private String passwd;

    @VerifyField("确认密码")
    private String newPasswd;

    @VerifyField("新的用户名")
    private String newName;
}
