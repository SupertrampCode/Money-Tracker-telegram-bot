package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.cache.BotStateCache;
import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.handlers.CallBackQueryHandler;
import com.example.sunny_money_bot.handlers.MessageHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    final MessageHandler messageHandler;
    final CallBackQueryHandler callBackQueryHandler;
    final BotStateCache botStateCache;

    @Autowired
    public TelegramFacade(MessageHandler messageHandler, CallBackQueryHandler callBackQueryHandler, BotStateCache botStateCache) {
        this.messageHandler = messageHandler;
        this.callBackQueryHandler = callBackQueryHandler;
        this.botStateCache = botStateCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()) {
            handleMsg(update.getMessage());
        }
        return null;
    }

    public BotApiMethod<?> handleMsg(Message message) {
        String inputMsg = message.getText();
        BotState botState;
        if (inputMsg.matches("[0-9]")) {
            botState = botStateCache.getLastUserBotState(message.getFrom().getId());
            switch (botState) {
                case ENTER_NEW_INCOME:
                    botState = BotState.ENTER_SUM_OF_INCOME;
                    return messageHandler.handle(message, botState);
                case ENTER_NEW_COSTS:
                    botState = BotState.ENTER_SUM_OF_COST;
                    return messageHandler.handle(message, botState);
            }
        }
        switch (inputMsg) {
            case "/start":
                botState = BotState.REGISTRATION;
                break;
            case "New income":
                botState = BotState.ENTER_NEW_INCOME;
                break;
            case "New cost":
                botState = BotState.ENTER_NEW_COSTS;
                break;
            case "Get report":
                botState=BotState.GET_REPORT;
                break;
            default:
                botState=BotState.START;
                return messageHandler.handle(message,botState);
        }
        return messageHandler.handle(message,botState);
    }


}
