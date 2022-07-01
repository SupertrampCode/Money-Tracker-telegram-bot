package com.example.sunny_money_bot.config;

import lombok.AccessLevel;
import lombok.Getter;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotConfig {

    @Value("${telegram.webhookPath}")
    String webHookPath;
    @Value("${telegrambot.botName}")
    String userName;
    @Value("${telegrambot.botToken}")
    String botToken;
}
