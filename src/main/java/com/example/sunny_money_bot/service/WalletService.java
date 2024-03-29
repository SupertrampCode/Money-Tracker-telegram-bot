package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public void changeSum(Long id, BigDecimal newBalance) {
        Wallet newWallet = findById(id).get();
        newWallet.setBalance(newBalance);
        save(newWallet);
    }
}
