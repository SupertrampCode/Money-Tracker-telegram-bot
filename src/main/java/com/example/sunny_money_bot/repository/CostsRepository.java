package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.Costs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostsRepository extends JpaRepository<Costs,Long> {
}
