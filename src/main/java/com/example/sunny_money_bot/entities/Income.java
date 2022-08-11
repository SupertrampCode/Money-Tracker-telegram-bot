package com.example.sunny_money_bot.entities;

import com.example.sunny_money_bot.enums.TransactionsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@NoArgsConstructor
public class Income extends Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionsType type;

    @Column(name = "sum")
    @Min(value=1)
    private BigDecimal sum;

    @Column(name = "reason")
    private String name;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id",nullable = false)
    private Wallet wallet;
}
