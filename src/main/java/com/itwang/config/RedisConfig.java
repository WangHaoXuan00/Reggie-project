package com.itwang.config;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-11 19:33
 */

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @packageName: com.itwang.config
 * @ClassName: RedisConfig
 * @Description:
 * @author Whx
 * @data 2022/2/11 TIME:19:33
 * */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<Object,Object>
    redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        //默认的K-ey序列化器为：JdkSerializationRedisSerializer
//        redis当中key的序列化器：
        redisTemplate.setKeySerializer(new StringRedisSerializer());//便于观察Redis key
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }



}
