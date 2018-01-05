package cn.xz.web;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MySpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_cfg.xml"})
public class MySpringTest {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	@Autowired
	private RedisBaseDao<Object> baseDao;
	
	
	@Test
	public void bbb(){
		System.out.println(redisTemplate.getConnectionFactory().getConnection());
		System.out.println(baseDao.toString());
	}
}
