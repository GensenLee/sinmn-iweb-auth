package com.sinmn.iweb.auth.redis;

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

}
