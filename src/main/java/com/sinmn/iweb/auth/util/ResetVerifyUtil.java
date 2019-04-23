package com.sinmn.iweb.auth.util;

import com.sinmn.core.utils.util.DateUtil;
import com.sinmn.iweb.auth.vo.innerVO.AuthUserResetVO;

import java.util.Random;

/**
 * @author Gensen.Lee
 * @date 2019/4/23 10:39
 */
public class ResetVerifyUtil {

    public static String verify(AuthUserResetVO reqVO, AuthUserResetVO redisVO){
        if (!reqVO.equals(redisVO)) {
            return "请求不存在。";
        }
        float hours = DateUtil.compareHours(reqVO.getReqTime(), redisVO.getReqTime());
        if (hours>=12) {
            return "服务超时，请重新发送邮件。";
        }
        return "";
    }

    /**生成指定长度的随机字符串 包含大小写 数字
     * @param length
     * @return
     */
    public static String randomToken(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            //随机数由0-9，a-z,A-Z组成，数字占10个，字母占52个，数字、字母占比1:5（标准的应该是10:52）
            //random.nextInt(6) 0-5中6个数取一个
            String charOrNum = (random.nextInt(6)+6) % 6 >=1 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母，输出比例为1:1
                int temp = random.nextInt(2) % 2 == 0 ? 97 : 65;
                //char（65）-char(90) 为大写字母A-Z；char(97)-char(122)为小写字母a-z
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
