package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
