package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.service.KeyboardService;
import com.example.sunny_money_bot.service.SendMessageService;
import com.example.sunny_money_bot.service.impl.UserService;
import com.example.sunny_money_bot.service.impl.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class MessageHandler implements Handler<Message, BotState> {

    private final UserService userService;
    private final KeyboardService keyboardService;

    private final WalletService walletService;
    private final SendMessageService sendMessageService;
    @Value("${msg.successful.reg}")
    String regMsg;

    @Value("${reg.msg}")
    private String hiMsg;

    @Autowired
    public MessageHandler(UserService userService,
                          KeyboardService keyboardService,
                          SendMessageService sendMessageService,
                          WalletService walletService) {
        this.userService = userService;
        this.keyboardService = keyboardService;
        this.sendMessageService = sendMessageService;
        this.walletService = walletService;
    }

    //TODO keyboard doesn't work
    @Override
    public BotApiMethod<?> handle(Message message, BotState botState) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        switch (botState) {
            case REGISTRATION:
                if (userService.isExist(userId)) {
                    return sendMessageService
                            .createMsgWithReplyKeyboard(chatId, hiMsg, keyboardService.mainMenuKeyboard());
                } else {
                    userService.saveNewUser(message, userId);
                    return sendMessageService
                            .createMsgWithReplyKeyboard(chatId,
                                    regMsg,
                                    keyboardService.mainMenuKeyboard());
                }
            case ENTER_NEW_INCOME:


        }
        return null;
    }
}
