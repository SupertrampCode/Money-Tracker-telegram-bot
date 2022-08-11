package com.example.sunny_money_bot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table( name = "wallets")
@Getter
@Setter
@NoArgsConstructor
public class Wallet {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name="balance")
    private BigDecimal balance;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_Id")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private Set<Income> incomeList;

    @OneToMany(mappedBy = "wallet")
    private Set<Costs> costsList;
}
