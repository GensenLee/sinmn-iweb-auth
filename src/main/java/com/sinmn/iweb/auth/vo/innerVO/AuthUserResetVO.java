package com.sinmn.iweb.auth.vo.innerVO;

import com.sinmn.core.utils.util.IntUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.vo.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 * @author Gensen.Lee
 * @date 2019/4/22 17:34
 */
@Data
public class AuthUserResetVO extends BaseBean {

    private Long userId;
    //请求时间
    private Date reqTime;
    //随机token
    private String resetToken1;
    private String resetToken2;


    public AuthUserResetVO(String userId,String resetToken1,String resetToken2){
        this.userId = LongUtil.toLong(userId);
        this.resetToken1 = resetToken1;
        this.resetToken2 = resetToken2;
        this.reqTime = new Date();
    }

    public AuthUserResetVO(String json){
        super(json);
    }

    public AuthUserResetVO(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUserResetVO that = (AuthUserResetVO) o;
        if (that.getResetToken1().equals(this.resetToken1)&&that.getResetToken2().equals(this.resetToken2)
                &&that.getUserId().equals(this.userId)) {
            return true;
        }
        return false;
    }
}
