package cn.xz.web;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisBaseDao<V> {

	@Autowired
	@Qualifier(value="redisTemplate")
	private RedisTemplate<String, V> redisTemplate;

	public void remove(String key) {
		redisTemplate.delete(key);
	}
	
	public V get(String key) {
		ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
		V v = valueOperations.get(key);
		return v;
	}
	
	public void set(String key, V v, int days){
		ValueOperations<String, V> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, v, days, TimeUnit.DAYS);
	}
}
