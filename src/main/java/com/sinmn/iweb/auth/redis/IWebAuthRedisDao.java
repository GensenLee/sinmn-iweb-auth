package com.sinmn.iweb.auth.redis;

import com.sinmn.iweb.auth.vo.innerVO.AuthUserResetVO;
import org.springframework.stereotype.Component;

import com.sinmn.core.utils.redis.AspectRedisDao;
import com.sinmn.core.utils.redis.annotation.Redis;
import com.sinmn.core.utils.util.FastJsonUtils;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

import redis.clients.jedis.Jedis;

@Component
public class IWebAuthRedisDao extends AspectRedisDao {

	private String KEY_TEMPLATE = "SINMN.IWEB.AUTH.SESSION_KEY:%s";

    private String USER_RESET_KEY = "USER.PWD.S:%s";

	private int expireTime = 60*60*24;
	
	@Redis
	public UserInfoInnerVO get(String sessionKey){
		Jedis jedis = getJedis();
		String key = String.format(KEY_TEMPLATE, sessionKey);
		String value = jedis.get(key);
		if(StringUtil.isNotEmpty(value)){
			//重新设置过期时间
			jedis.setex(key, expireTime, value);
		}
		return FastJsonUtils.getBean(value, UserInfoInnerVO.class);
	}
	
	
	@Redis
	public void set(UserInfoInnerVO userInfoInnerVO){
		Jedis jedis = getJedis();
		String key = String.format(KEY_TEMPLATE, userInfoInnerVO.getSessionKey());
		jedis.setex(key, expireTime, userInfoInnerVO.toJsonString());
	}

    @Redis
    public String getByKey(String key){
        Jedis jedis = getJedis();
        String k = String.format(KEY_TEMPLATE, key);
        String value = jedis.get(k);
        if(StringUtil.isNotEmpty(value)){
            //重新设置过期时间
            jedis.setex(k, expireTime, value);
        }
        return value;
    }

    @Redis
    public void set(String key,String value){
        Jedis jedis = getJedis();
        String k = String.format(KEY_TEMPLATE, key);
        jedis.setex(k, expireTime, value);
    }

    @Redis
    public AuthUserResetVO getUserResetVO(String userId){
        String key = String.format(USER_RESET_KEY, userId);
        String json = getByKey(key);

        if (StringUtil.isEmpty(json)) {
            return new AuthUserResetVO();
        }else {
            return new AuthUserResetVO(json);
        }
    }

    @Redis
    public void setUserResetVO(AuthUserResetVO vo){
        set(String.format(USER_RESET_KEY,String.valueOf(vo.getUserId())),vo.toJsonString());
    }
    @Redis
    public void removeUserResetVO(String userId){
        set(String.format(USER_RESET_KEY,userId),"{}");
    }
}
