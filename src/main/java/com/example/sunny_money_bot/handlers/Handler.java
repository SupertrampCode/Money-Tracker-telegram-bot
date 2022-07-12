package com.example.sunny_money_bot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface Handler<T,K> {

    BotApiMethod<?> handle (T data,K botState);
}
