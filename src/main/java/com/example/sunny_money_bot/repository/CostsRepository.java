package com.example.sunny_money_bot.repository;

import com.example.sunny_money_bot.entities.Costs;
import com.example.sunny_money_bot.enums.TransactionFrequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CostsRepository extends JpaRepository<Costs,Long> {

    List<Costs> getAllByTransactionFrequency(TransactionFrequency transactionFrequency);

    List<Costs> getAllByTimeBetween (LocalDateTime timeStart, LocalDateTime timeEnd);
}
