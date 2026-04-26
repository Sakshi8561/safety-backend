package com.sakshi.chatapp.safety_backend.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public NotificationService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void handleNotification(Long userId, Long botId) {

        String key = "notif:user:" + userId;

        Boolean exists = redisTemplate.hasKey(key);

        if (Boolean.TRUE.equals(exists)) {

            // store in list
            redisTemplate.opsForList().rightPush(
                "user:" + userId + ":pending_notifs",
                "Bot " + botId + " replied"
            );

        } else {

            // instant notification
            System.out.println("Push Notification Sent to User " + userId);

            redisTemplate.opsForValue().set(
                key,
                "1",
                15,
                TimeUnit.MINUTES
            );
        }
    }
}