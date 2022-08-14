package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.User;
import com.example.sunny_money_bot.entities.Wallet;
import com.example.sunny_money_bot.repository.UserRepository;
import com.example.sunny_money_bot.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public void saveNewUser(Message message) {
        String userName = message.getFrom().getUserName();
        long userId = message.getFrom().getId();
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setId(userId);
        user.setWallet(wallet);
        userRepository.save(user);
        walletRepository.save(wallet);
    }

}
