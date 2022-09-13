package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.Income;
import com.example.sunny_money_bot.enums.TransactionFrequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Long> {

    List<Income> getAllByTransactionFrequency(TransactionFrequency transactionFrequency);

    List<Income> getAllByTimeBetween (LocalDateTime timeStart, LocalDateTime timeEnd);
}
