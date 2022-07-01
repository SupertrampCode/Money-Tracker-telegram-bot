package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income,Long> {
}
