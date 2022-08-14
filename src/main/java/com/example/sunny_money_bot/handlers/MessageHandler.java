package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.repository.UserRepository;
import com.example.sunny_money_bot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class MessageHandler implements Handler<Message, BotState> {

    private final UserService userService;
    private final KeyboardService keyboardService;
    private final SendMessageService sendMessageService;

    @Value("${msg.successful.reg}")
    String regMsg;

    @Value("${reg.msg}")
    private String hiMsg;

    @Value("${msg.enterTransactionType}")
    private String chooseTransactionTypeMsg;

    @Value("${msg.enterTransactionSum}")
    private String enterTransactionSum;

    @Value("${msg.succesfulTransactionSave}")
    private String transactionSaved;

    private final UserRepository userRepository;


    public MessageHandler(UserService userService,
                          KeyboardService keyboardService,
                          SendMessageService sendMessageService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.keyboardService = keyboardService;
        this.sendMessageService = sendMessageService;
        this.userRepository = userRepository;
    }

    @Override
    public BotApiMethod<?> handle(Message message, BotState botState) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        switch (botState) {
            case REGISTRATION:
                if (userRepository.existsById(userId)) {
                    return sendMessageService
                            .createMsgWithReplyKeyboard(chatId,
                                    hiMsg,
                                    keyboardService.mainMenuKeyboard());
                } else {
                    userService.saveNewUser(message);
                    return sendMessageService
                            .createMsgWithReplyKeyboard(chatId,
                                    regMsg,
                                    keyboardService.mainMenuKeyboard());
                }
            case ENTER_NEW_INCOME:
            case ENTER_NEW_COSTS:
                return sendMessageService.sendSimpleMessage(chatId, enterTransactionSum);
            case ENTER_SUM_OF_COST:
            case ENTER_SUM_OF_INCOME:
                return sendMessageService
                        .createMsgWithInlineKeyboard(chatId,
                                chooseTransactionTypeMsg,
                                keyboardService.yesNoInlineKeyboard());
            case ENTER_REASON_OF_COST:
            case ENTER_INCOME_REASON:
                return sendMessageService.
                        createMsgWithReplyKeyboard(chatId,
                                transactionSaved,
                                keyboardService.mainMenuKeyboard());
            case START:
                return sendMessageService.
                        createMsgWithReplyKeyboard(chatId,
                                hiMsg,
                                keyboardService.mainMenuKeyboard());
        }
        return null;
    }
}
