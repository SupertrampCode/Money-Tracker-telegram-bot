package com.example.sunny_money_bot.entities;

import com.example.sunny_money_bot.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name="state")
    private State state;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;
}
