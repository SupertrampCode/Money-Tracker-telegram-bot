package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.service.KeyboardService;
import com.example.sunny_money_bot.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallBackQueryHandler implements Handler<CallbackQuery, BotState> {

    @Value("${msg.chooseFrequencyOfTransaction}")
    private String chooseFreq;

    @Value("${msg.enterCostPriority}")
    private String choosePriority;

    @Value("${msg.enterTransactionReason}")
    private String enterReason;
    private final SendMessageService sendMessageService;

    private final KeyboardService keyboardService;

    @Autowired
    public CallBackQueryHandler(SendMessageService sendMessageService, KeyboardService keyboardService) {
        this.sendMessageService = sendMessageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery data, BotState botState) {
        Long chatId = data.getFrom().getId();
        switch (botState) {
            case ADD_NEW_REGULAR_COST:
            case ADD_NEW_REGULAR_INCOME:
                return sendMessageService
                        .createMsgWithInlineKeyboard(
                                chatId,
                                chooseFreq,
                                keyboardService.frequencyKeyboard());
            case ENTER_INCOME_FREQUENCY:
            case ADD_NEW_SINGLE_INCOME:
            case ENTER_PRIORITY_NEW_COST:
                return sendMessageService.createMsgWithReplyKeyboard(
                        chatId,
                        enterReason,
                        keyboardService.mainMenuKeyboard());
            case ADD_NEW_SINGLE_COST:
            case ENTER_COSTS_FREQUENCY:
                return sendMessageService.createMsgWithInlineKeyboard(
                        chatId,
                        choosePriority,
                        keyboardService.costPriorityKeyboard()
                );
        }
        return null;
    }
}
