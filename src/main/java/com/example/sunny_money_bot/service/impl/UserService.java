package com.example.sunny_money_bot.service.impl;

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

    @Value("${msg.successful.reg}")
    private String successRegMsg;

    @Autowired
    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean isExist(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public void saveNewUser(Message message, long userId) {
        String userName = message.getFrom().getUserName();
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setId(userId);
        user.setWallet(wallet);
        save(user);
        walletRepository.save(wallet);
    }

}
