package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.enums.TransactionsType;

public interface TransactionsService {
    void createTransactionAndSetSum (String sum);
    void setTransactionFrequency (String callBackData);
    void setTransactionTypeAndTime (TransactionsType transactionsType);
}
