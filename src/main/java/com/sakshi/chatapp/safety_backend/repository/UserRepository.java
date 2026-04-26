package com.sakshi.chatapp.safety_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakshi.chatapp.safety_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
