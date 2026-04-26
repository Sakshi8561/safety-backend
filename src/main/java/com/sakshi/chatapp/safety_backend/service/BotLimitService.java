package com.sakshi.chatapp.safety_backend.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BotLimitService {

    private final RedisTemplate<String, Object> redisTemplate;

    public BotLimitService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean canBotInteract(Long postId) {

        String key = "post:" + postId + ":bot_count";

        //Atomic increment (thread-safe)
        Long count = redisTemplate.opsForValue().increment(key, 1);

        System.out.println("Bot count for post " + postId + " = " + count);

        // Reject if exceeds 100
        if (count != null && count > 100) {
            return false;
        }

        return true;
    }
}