package fun.kylen.kojservice.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: KyLen
 * @Date: 2024/8/4 23:25
 * @Description:
 */
@Component
@SuppressWarnings("all")
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> r;

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object get(String key) {
        return StrUtil.isBlank(key) ? null : r.opsForValue().get(key);
    }

    /**
     * 获取缓存，指定类
     * @param key
     * @return
     */
    public <T> T get(String key, Class<T> type) {
        try {
            return StrUtil.isBlank(key) ? null : (T) r.opsForValue().get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存，字符串
     * @param key
     * @return
     */
    public String getStr(String key) {
        try {
            return StrUtil.isBlank(key) ? null : (String) r.opsForValue().get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置缓存，失败返回false
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            r.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置缓存并设置时间（秒），失败返回false
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                r.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key
     * @return 返回0代表为永久有效
     */
    public Long getExpireTime(String key) {
        return r.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return r.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean del(String key) {
        try {
            return r.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
