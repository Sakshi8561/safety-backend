package com.sakshi.chatapp.safety_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sakshi.chatapp.safety_backend.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}