package com.sakshi.chatapp.safety_backend.scheduler;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final RedisTemplate<String, Object> redisTemplate;

    public NotificationScheduler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedRate = 300000) // 5 min
    public void processNotifications() {

        Set<String> keys = redisTemplate.keys("user:*:pending_notifs");

        if (keys == null) return;

        for (String key : keys) {

            Long count = redisTemplate.opsForList().size(key);

            if (count != null && count > 0) {

                System.out.println(
                    "Summarized Push Notification: Bot X and "
                    + count + " others interacted with your posts."
                );

                redisTemplate.delete(key);
            }
        }
    }
}