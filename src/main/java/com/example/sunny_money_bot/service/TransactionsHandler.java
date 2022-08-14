package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.Costs;
import com.example.sunny_money_bot.entities.Income;
import com.example.sunny_money_bot.entities.Transaction;
import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.enums.TransactionFrequency;
import com.example.sunny_money_bot.repository.CostsRepository;
import com.example.sunny_money_bot.repository.IncomeRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class TransactionsHandler {

    private final WalletService walletService;

    private final IncomeRepository incomeRepository;

    private final CostsRepository costsRepository;

    public TransactionsHandler(WalletService walletService, IncomeRepository incomeRepository, CostsRepository costsRepository) {
        this.walletService = walletService;
        this.incomeRepository = incomeRepository;
        this.costsRepository = costsRepository;
    }

    public void updateWalletBalance(Transaction transaction) {
        long userId = transaction.getWallet().getId();
        Wallet wallet = walletService.findById(userId).get();
        BigDecimal walletBalance=wallet.getBalance();
        if (walletBalance==null){
            walletBalance=BigDecimal.ZERO;
        }
        if (transaction.getClass() == Costs.class) {
            try {
                BigDecimal newBalance = walletBalance.subtract(transaction.getSum());
                walletService.changeSum(userId, newBalance);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        } else if (transaction.getClass() == Income.class) {
            BigDecimal newBalance = walletBalance.add(transaction.getSum());
            walletService.changeSum(userId, newBalance);
        } else throw new IllegalArgumentException("Update Wallet Balance got wrong value!");
    }

    //TODO make regular changing balance, add choose of transactions frequency.
    @Scheduled(cron = "0 0 0 * * *")
    public void everyDayTransactionsHandler() {
        Set<Transaction> allTransactions = new HashSet<>();
        allTransactions.addAll(incomeRepository.findAll());
        allTransactions.addAll(costsRepository.findAll());
        List<Transaction>everyDayTransactions=allTransactions.stream()
                .filter(transaction -> transaction.getTransactionFrequency() == TransactionFrequency.EVERY_DAY)
                .collect(Collectors.toList());
        if (everyDayTransactions.size()!=0){
        saveTransactionsWithNewDate(everyDayTransactions);
                everyDayTransactions.forEach(this::updateWalletBalance);
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void everyMonthTransactionHandler() {
        Set<Transaction> allTransactions = new HashSet<>();
        allTransactions.addAll(incomeRepository.findAll());
        allTransactions.addAll(costsRepository.findAll());
        int monthNumber = LocalDateTime.now().getMonthValue();
        if (monthNumber == 1) {
            List<Transaction> decemberTransactionsList = allTransactions.stream()
                    .filter(transaction -> transaction.getTransactionFrequency() == TransactionFrequency.EVERY_MONTH)
                    .filter(transaction -> transaction.getTime().getMonthValue() == 12)
                    .collect(Collectors.toList());
            if (decemberTransactionsList.size() != 0) {
                saveTransactionsWithNewDate(decemberTransactionsList);
                decemberTransactionsList.forEach(this::updateWalletBalance);
            }
        } else {
            List<Transaction> transactionsList = allTransactions.stream()
                    .filter(transaction -> transaction.getTransactionFrequency() == TransactionFrequency.EVERY_MONTH)
                    .filter(transaction -> transaction.getTime().getMonthValue() + 1 == monthNumber)
                    .collect(Collectors.toList());
            if (transactionsList.size() != 0) {
                saveTransactionsWithNewDate(transactionsList);
                transactionsList.forEach(this::updateWalletBalance);
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void everyYearTransactionHandler() {
        Set<Transaction> allTransactions = new HashSet<>();
        allTransactions.addAll(incomeRepository.findAll());
        allTransactions.addAll(costsRepository.findAll());
        int year = LocalDateTime.now().getYear();
        List<Transaction> everyYearTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getTime().getYear() + 1 == year)
                .collect(Collectors.toList());
        if (everyYearTransactions.size() != 0) {
            saveTransactionsWithNewDate(everyYearTransactions);
            everyYearTransactions.forEach(this::updateWalletBalance);
        }
    }

    public void saveTransactionsWithNewDate(List<Transaction> transactions) {
            transactions.forEach(transaction -> transaction.setTime(LocalDateTime.now()));
            for (Transaction transaction : transactions) {
                if (transaction.getClass() == Income.class) {
                    incomeRepository.save((Income) transaction);
                } else {
                    costsRepository.save((Costs) transaction);
                }
            }
    }
}
