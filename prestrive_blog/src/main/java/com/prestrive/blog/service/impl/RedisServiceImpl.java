package com.prestrive.blog.service.impl;



import com.prestrive.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 读写Redis相关操作
 *（set、String、hash、list、sortedList）
 * @author: fanfanli
 * @date: 2021/7/27
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Qualifier("redisTemplateJackson")
	@Autowired()
	RedisTemplate jsonRedisTemplate;

	/**
	 * 通过key删除一个redis中的结构（set、String、hash、list、sortedList）
	 *
	 * @param key
	 * @return void
	 */
	@Override
	public void deleteCacheByKey(String key) {
		jsonRedisTemplate.delete(key);
	}

	/**
	 * 查询redis中是否存在某个key
	 *
	 * @param key
	 * @return boolean
	 */
	@Override
	public boolean hasKey(String key) {
		return jsonRedisTemplate.hasKey(key);
	}
	/**
	 *批量删除key 其中 keys为 Collection<K>
	 */
	@Override
	public void deleteAllKeys() {
		jsonRedisTemplate.delete(jsonRedisTemplate.keys("*"));
	}

	/**
	 * 设置 键为key的记录 的过期过期时间
	 * @param key
	 * @param secondes
	 */
	@Override
	public void expire(Object key, int secondes) {
		jsonRedisTemplate.expire(key,secondes, TimeUnit.SECONDS);
	}





	//set 数据类型

	/**
	 * 通过key向set中加入一个值（对象）
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void saveValueToSet(String key, Object value) {
		jsonRedisTemplate.opsForSet().add(key, value);
	}

	/**
	 * 通过key从set中删除一个值（对象）
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	@Override
	public void deleteValueBySet(String key, Object value) {
		jsonRedisTemplate.opsForSet().remove(key, value);
	}

	/**
	 * 通过key查询set中是否有某个值（对象）
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	@Override
	public boolean hasValueInSet(String key, Object value) {
		return jsonRedisTemplate.opsForSet().isMember(key, value);
	}






	//hash 数据类型

	/**
	 * 新增hashMap值
	 * @param hash Redis的key
	 * @param key key对应的map值的key
	 * @param value 为key对应的map值的值
	 */
	@Override
	public void saveKVToHash(String hash, Object key, Object value) {
		jsonRedisTemplate.opsForHash().put(hash, key, value);
	}

	@Override
	public void saveMapToHash(String hash, Map map) {
		jsonRedisTemplate.opsForHash().putAll(hash, map);
	}

	/**
	 * 获取key对应的所有map键值对 如：{map1 = value1,map2 = value2 }
	 * @param hash
	 * @return
	 */
	@Override
	public Map getMapByHash(String hash) {
		return jsonRedisTemplate.opsForHash().entries(hash);
	}

	/**
	 * 	获取 (hash) 对应的 map 中，key为 (key) 的map的对应的值(value1)
	 * @param hash
	 * @param key
	 * @return
	 */
	@Override
	public Object getValueByHashKey(String hash, Object key) {
		return jsonRedisTemplate.opsForHash().get(hash, key);
	}

	/**
	 * 判断 key(为hash)对应的map中是否有指定的键(key)
	 * @param hash
	 * @param key
	 * @return
	 */
	@Override
	public boolean hasHashKey(String hash, Object key) {
		return jsonRedisTemplate.opsForHash().hasKey(hash,key);
	}

	/**
	 * 使 key(为hash)对应的map中，键(key)对应的值以(increment)自增
	 * @param hash
	 * @param key
	 * @param increment
	 */
	@Override
	public void incrementByHashKey(String hash, Object key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForHash().increment(hash, key, increment);
	}

	/**
	 * 通过hash和key删除一条hash记录
	 *
	 * @param hash
	 * @param key
	 */
	@Override
	public void deleteByHashKey(String hash, Object key) {
		jsonRedisTemplate.opsForHash().delete(hash, key);
	}

	@Override
	public int countBySet(String key) {
		return jsonRedisTemplate.opsForSet().size(key).intValue();

	}





	// string 数据类型

	/**
	 * 设置 键为key的值 以 increment 进行自增
	 * @param key
	 * @param increment
	 */
	@Override
	public void incrementByKey(String key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 * 新增一个字符串类型的值
	 * @param key
	 * @param object
	 */
	@Override
	public void saveObjectToValue(String key, Object object) {
		jsonRedisTemplate.opsForValue().set(key, object);
	}

	/**
	 * 获取指定的 key 对应的值
	 * @param key
	 * @return
	 */
	@Override
	public  Object getObjectByValue(String key) {
		Object redisResult = jsonRedisTemplate.opsForValue().get(key);
		return redisResult;
	}


}
