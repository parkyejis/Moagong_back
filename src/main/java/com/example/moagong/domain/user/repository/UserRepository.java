package com.example.moagong.domain.user.repository;

import com.example.moagong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
