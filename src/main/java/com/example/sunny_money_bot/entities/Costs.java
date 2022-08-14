package com.example.sunny_money_bot.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "costs")
@Getter
@Setter
@NoArgsConstructor
public class Costs extends Transaction {

    @Column(name = "priority")
    @Max(value = 5, message = "Max value of priority can't be more than 5")
    @Min(value = 1, message = "Value of priority can't be 0 or smaller")
    private Integer priority;

}
