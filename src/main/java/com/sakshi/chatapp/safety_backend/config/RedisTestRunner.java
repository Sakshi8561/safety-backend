/*package com.sakshi.chatapp.safety_backend.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisTestRunner {

    @Bean
    public CommandLineRunner testRedis(RedisTemplate<String, Object> redisTemplate) {
        return args -> {
            redisTemplate.opsForValue().set("test", "hello");

            Object value = redisTemplate.opsForValue().get("test");

            System.out.println("Redis Value: " + value);
        };
    }
}*/