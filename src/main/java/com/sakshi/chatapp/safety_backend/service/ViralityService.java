package com.sakshi.chatapp.safety_backend.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViralityService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ViralityService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void increaseScore(Long postId, int points) {

        String key = "post:" + postId + ":virality_score";

        Long value = redisTemplate.opsForValue().increment(key, points);

        System.out.println("Virality Score Updated: " + value);
    }

    public Long getScore(Long postId) {

        String key = "post:" + postId + ":virality_score";

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) return 0L;

        return Long.parseLong(value.toString());
    }
}