package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.Costs;
import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.enums.TransactionFrequency;
import com.example.sunny_money_bot.enums.TransactionsType;
import com.example.sunny_money_bot.repository.CostsRepository;
import com.example.sunny_money_bot.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CostsService implements TransactionsService {
    private final CostsRepository costsRepository;

    private final WalletRepository walletRepository;

    private final TransactionsHandler transactionsHandler;

    private Costs costs;

    @Autowired
    public CostsService(CostsRepository costsRepository, WalletRepository walletRepository, TransactionsHandler transactionsHandler) {
        this.costsRepository = costsRepository;
        this.walletRepository = walletRepository;
        this.transactionsHandler = transactionsHandler;
    }

    public void createTransactionAndSetSum(String sum){
        costs=new Costs();
        costs.setSum(new BigDecimal(sum));
    }

    public void setTransactionTypeAndTime(TransactionsType type){
        costs.setType(type);
        costs.setTime(LocalDateTime.now());
    }

    public void setTransactionFrequency(String callBackData){
        switch (callBackData){
            case "everyDay":
                costs.setTransactionFrequency(TransactionFrequency.EVERY_DAY);
                break;
            case "everyMonth":
                costs.setTransactionFrequency(TransactionFrequency.EVERY_MONTH);
            case "everyYear":
                costs.setTransactionFrequency(TransactionFrequency.EVERY_YEAR);
        }
    }

    public void setCostPriority (String callBackData){
        switch (callBackData){
            case "firstPriority":
                costs.setPriority(1);
                break;
            case "secondPriority":
                costs.setPriority(2);
                break;
            case "thirdPriority":
                costs.setPriority(3);
                break;
            case "fourthPriority":
                costs.setPriority(4);
                break;
            case "fifthPriority":
                costs.setPriority(5);
                break;
        }
    }
    public void setCostReasonAndSaveCost (String reason,Long userId){
        costs.setReason(reason);
        Wallet wallet= walletRepository.findById(userId).get();
        Set<Costs> costsList = wallet.getCostsList();
        if (costsList==null){
            costsList=new HashSet<>();
        }
        costsList.add(costs);
        wallet.setCostsList(costsList);
        costs.setWallet(wallet);
        costsRepository.save(costs);
        walletRepository.save(wallet);
        transactionsHandler.updateWalletBalance(costs);
    }
}
