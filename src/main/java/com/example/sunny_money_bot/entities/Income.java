package com.example.sunny_money_bot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "incomes")
@Getter
@Setter
@NoArgsConstructor
public class Income extends Transaction {

}
