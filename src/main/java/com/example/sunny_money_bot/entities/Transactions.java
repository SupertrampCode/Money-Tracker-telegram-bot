package com.example.sunny_money_bot.entities;

import com.example.sunny_money_bot.enums.TransactionsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Transactions {
    private BigDecimal sum;
    private LocalDateTime time;
    private TransactionsType type;
}
