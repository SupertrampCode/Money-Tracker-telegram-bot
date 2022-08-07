package com.example.sunny_money_bot.entities;

import com.example.sunny_money_bot.enums.TransactionsType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "incomes")
@Getter
@Setter
public class Income extends Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionsType type;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "reason")
    private String name;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
