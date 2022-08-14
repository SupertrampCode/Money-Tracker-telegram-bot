package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.cache.BotStateCache;
import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.enums.TransactionsType;
import com.example.sunny_money_bot.handlers.CallBackQueryHandler;
import com.example.sunny_money_bot.handlers.MessageHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {
    private final MessageHandler messageHandler;
    private final CallBackQueryHandler callBackQueryHandler;
    private final BotStateCache botStateCache;

    private final IncomesService incomesService;

    private final CostsService costsService;

    private BotState botState;

    public TelegramFacade(MessageHandler messageHandler,
                          CallBackQueryHandler callBackQueryHandler,
                          BotStateCache botStateCache,
                          IncomesService incomesService,
                          CostsService costsService) {
        this.messageHandler = messageHandler;
        this.callBackQueryHandler = callBackQueryHandler;
        this.botStateCache = botStateCache;
        this.incomesService = incomesService;
        this.costsService = costsService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()) {
            return handleMsg(update.getMessage());
        } else {
            return handleCBQ(update.getCallbackQuery());
        }
    }


    public BotApiMethod<?> handleMsg(Message message) {
        String inputMsg = message.getText();
        String numericInputMsg = inputMsg.trim();
        Long userId = message.getFrom().getId();
        if (numericInputMsg.matches("[0-9]+")) {
            return handleNumericMsg(message);
        }
        switch (inputMsg) {
            case "/start":
                botState = BotState.REGISTRATION;
                botStateCache.saveBotState(userId, botState);
                break;
            case "New income":
                botState = BotState.ENTER_NEW_INCOME;
                botStateCache.saveBotState(userId, botState);
                break;
            case "New cost":
                botState = BotState.ENTER_NEW_COSTS;
                botStateCache.saveBotState(userId, botState);
                break;
            case "Get report":
                botState = BotState.GET_REPORT;
                botStateCache.saveBotState(userId, botState);
                break;
            default:
                if (botStateCache
                        .getLastUserBotState(userId) == BotState.ENTER_PRIORITY_NEW_COST) {
                    botState = BotState.ENTER_REASON_OF_COST;
                    botStateCache.saveBotState(userId, botState);
                    costsService.setCostReasonAndSaveCost(inputMsg, userId);
                    return messageHandler.handle(message, botState);
                } else if (botStateCache
                        .getLastUserBotState(userId) == BotState.ADD_NEW_SINGLE_INCOME) {
                    botState = BotState.ENTER_INCOME_REASON;
                    botStateCache.saveBotState(userId, botState);
                    incomesService.setIncomeReasonAndSaveIncome(inputMsg, userId);
                    return messageHandler.handle(message, botState);
                } else if (botStateCache
                        .getLastUserBotState(userId) == BotState.ENTER_INCOME_FREQUENCY) {
                    botState = BotState.ENTER_INCOME_REASON;
                    botStateCache.saveBotState(userId, botState);
                    incomesService.setIncomeReasonAndSaveIncome(inputMsg, userId);
                    return messageHandler.handle(message, botState);
                }
                botState = BotState.START;
                botStateCache.saveBotState(userId, botState);
                return messageHandler.handle(message, botState);
        }
        return messageHandler.handle(message, botState);
    }


    public BotApiMethod<?> handleNumericMsg(Message message) {
        String numericInputMsg = message.getText().trim();
        Long userId = message.getFrom().getId();
        botState = botStateCache.getLastUserBotState(userId);
        switch (botState) {
            case ENTER_NEW_INCOME:
                botState = BotState.ENTER_SUM_OF_INCOME;
                botStateCache.saveBotState(userId, botState);
                incomesService.createTransactionAndSetSum(numericInputMsg);
                return messageHandler.handle(message, botState);
            case ENTER_NEW_COSTS:
                botState = BotState.ENTER_SUM_OF_COST;
                costsService.createTransactionAndSetSum(numericInputMsg);
                botStateCache.saveBotState(userId, botState);
                return messageHandler.handle(message, botState);
        }
        return null;
    }

    public BotApiMethod<?> handleCBQ(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        String callbackQueryData = callbackQuery.getData();
        switch (botState) {
            case ENTER_SUM_OF_INCOME:
                if (callbackQueryData.equals("buttonYes")) {
                    botState = BotState.ADD_NEW_REGULAR_INCOME;
                    botStateCache.saveBotState(userId, botState);
                    incomesService.setTransactionTypeAndTime(TransactionsType.REGULAR);
                } else if(callbackQueryData.equals("buttonNo")) {
                    botState = BotState.ADD_NEW_SINGLE_INCOME;
                    botStateCache.saveBotState(userId, botState);
                    incomesService.setTransactionTypeAndTime(TransactionsType.IRREGULAR);
                }
                break;
            case ADD_NEW_REGULAR_INCOME:
                botState = BotState.ENTER_INCOME_FREQUENCY;
                botStateCache.saveBotState(userId, botState);
                incomesService.setTransactionFrequency(callbackQueryData);
                break;
            case ENTER_SUM_OF_COST:
                if (callbackQueryData.equals("buttonYes")) {
                    botState = BotState.ADD_NEW_REGULAR_COST;
                    botStateCache.saveBotState(userId, botState);
                    costsService.setTransactionTypeAndTime(TransactionsType.REGULAR);
                } else if (callbackQueryData.equals("buttonNo")){
                    botState = BotState.ADD_NEW_SINGLE_COST;
                    botStateCache.saveBotState(userId, botState);
                    costsService.setTransactionTypeAndTime(TransactionsType.IRREGULAR);
                }
                break;
            case ADD_NEW_REGULAR_COST:
                botState = BotState.ENTER_COSTS_FREQUENCY;
                botStateCache.saveBotState(userId, botState);
                costsService.setTransactionFrequency(callbackQueryData);
                break;
            case ENTER_COSTS_FREQUENCY:
            case ADD_NEW_SINGLE_COST:
                botState = BotState.ENTER_PRIORITY_NEW_COST;
                botStateCache.saveBotState(userId, botState);
                costsService.setCostPriority(callbackQueryData);
                break;
        }
        return callBackQueryHandler.handle(callbackQuery, botState);
    }


}
