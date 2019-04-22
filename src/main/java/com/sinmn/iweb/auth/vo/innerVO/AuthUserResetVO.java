package com.sinmn.iweb.auth.vo.innerVO;

import com.sinmn.core.utils.vo.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 * @author Gensen.Lee
 * @date 2019/4/22 17:34
 */
@Data
public class AuthUserResetVO extends BaseBean {

    //请求时间
    private Date reqTime;
    //随机token
    private String resetToken1;
    private String resetToken2;

}
