package com.example.sunny_money_bot.entities;

import com.example.sunny_money_bot.enums.TransactionFrequency;
import com.example.sunny_money_bot.enums.TransactionsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum")
    @Min(value = 1)
    private BigDecimal sum;

    @Column(name = "reason")
    private String reason;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionsType type;

    @Column(name="frequency")
    @Enumerated(EnumType.STRING)
    private TransactionFrequency transactionFrequency;

    @ManyToOne
    @JoinColumn(name = "wallet_user_id",nullable = false)
    private Wallet wallet;
}
