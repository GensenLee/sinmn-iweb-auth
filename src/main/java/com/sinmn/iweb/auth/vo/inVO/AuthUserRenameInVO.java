package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gensen.Lee
 * @date 2019/4/26 10:22
 */
@Data
public class AuthUserRenameInVO extends BaseBean {

    @VerifyField("新的用户名")
    private String newName;

}
