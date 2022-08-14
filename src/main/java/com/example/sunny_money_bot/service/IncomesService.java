package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.Income;
import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.enums.TransactionFrequency;
import com.example.sunny_money_bot.enums.TransactionsType;
import com.example.sunny_money_bot.repository.IncomeRepository;
import com.example.sunny_money_bot.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class IncomesService implements TransactionsService{
    private final IncomeRepository incomeRepository;

    private final WalletRepository walletRepository;

    private final TransactionsHandler transactionsHandler;

    private Income income;

    @Autowired
    public IncomesService(IncomeRepository incomeRepository, WalletRepository walletRepository, TransactionsHandler transactionsHandler) {
        this.incomeRepository = incomeRepository;
        this.walletRepository = walletRepository;
        this.transactionsHandler = transactionsHandler;
    }

    public void createTransactionAndSetSum(String sum){
        income = new Income();
        income.setSum(new BigDecimal(sum.trim()));
    }

    public void setTransactionTypeAndTime(TransactionsType transactionsType){
        income.setType(transactionsType);
        income.setTime(LocalDateTime.now());
    }

    public void setTransactionFrequency (String callBackData){
        switch (callBackData){
            case "everyDay":
                income.setTransactionFrequency(TransactionFrequency.EVERY_DAY);
                break;
            case "everyMonth":
                income.setTransactionFrequency(TransactionFrequency.EVERY_MONTH);
            case "everyYear":
                income.setTransactionFrequency(TransactionFrequency.EVERY_YEAR);
        }
    }

    public void setIncomeReasonAndSaveIncome(String reason,Long userId){
        income.setReason(reason);
        Wallet wallet = walletRepository.findById(userId).get();
        Set<Income> incomeSet =wallet.getIncomeList();
        if (incomeSet==null){
            incomeSet=new HashSet<>();
        }
        incomeSet.add(income);
        wallet.setIncomeList(incomeSet);
        income.setWallet(wallet);
        incomeRepository.save(income);
        walletRepository.save(wallet);
        transactionsHandler.updateWalletBalance(income);
    }

}
