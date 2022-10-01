package com.prestrive.blog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.lettuce.LettucePool;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisManagerConfig {

    private String host;

    private String port;

    private String password;

    private Integer timeout;

    //private JedisPool jedisPool;


}
