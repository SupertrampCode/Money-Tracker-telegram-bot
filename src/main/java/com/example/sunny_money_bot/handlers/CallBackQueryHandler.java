package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.enums.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallBackQueryHandler implements Handler<CallbackQuery, BotState> {
    @Override
    public BotApiMethod<?> handle(CallbackQuery data, BotState botState) {
        return null;
    }
}
