package com.example.sunny_money_bot.entities;


import com.example.sunny_money_bot.enums.TransactionsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "costs")
@Getter
@Setter
@NoArgsConstructor
public class Costs extends Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionsType type;

    @Column(name = "sum")
    @Min(value = 1)
    private BigDecimal sum;

    @Column(name = "reason")
    private String reason;

    @Column(name = "priority")
    @Max(value = 5, message = "Max value of priority can't be more than 5")
    @Min(value = 1, message = "Value of priority can't be 0 or smaller")
    private Integer priority;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id",nullable = false)
    private Wallet wallet;
}
