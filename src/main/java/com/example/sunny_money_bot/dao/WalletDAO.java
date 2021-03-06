package com.example.sunny_money_bot.dao;

import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletDAO {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletDAO(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public void deleteById(Long id) {
        walletRepository.deleteById(id);
    }

    public boolean isExist(Long id) {
        return walletRepository.findById(id).isPresent();
    }
}
