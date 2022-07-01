package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
