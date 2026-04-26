package com.sakshi.chatapp.safety_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sakshi.chatapp.safety_backend.entity.Bot;

public interface BotRepository extends JpaRepository<Bot, Long> {
}