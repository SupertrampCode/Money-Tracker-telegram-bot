package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.dao.UserDAO;
import com.example.sunny_money_bot.enums.BotState;
import com.example.sunny_money_bot.service.KeyboardService;
import com.example.sunny_money_bot.service.SendMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message, BotState> {

    private final EventHandler eventHandler;
    private final KeyboardService keyboardService;
    private final UserDAO userDAO;
    private final SendMessageService sendMessageService;
    @Value("${reg.msg}")
    String regMsg;

    public MessageHandler(EventHandler eventHandler, KeyboardService keyboardService, UserDAO userDAO, SendMessageService sendMessageService) {
        this.eventHandler = eventHandler;
        this.keyboardService = keyboardService;
        this.userDAO = userDAO;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public BotApiMethod<?> handle(Message message, BotState botState) {
        Long userId=message.getFrom().getId();
        Long chatId=message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        switch (botState){
            case REGISTRATION:
                if (userDAO.isExist(userId)){
                    sendMessageService.createMessageWithReplyKeyboard(chatId,"Hi",keyboardService.mainMenuKeyboard());
                }
                else {
                    eventHandler.saveNewUser(message,userId);
                    sendMessageService.createMessageWithReplyKeyboard(chatId,regMsg,keyboardService.mainMenuKeyboard());
                }

        }
        return null;
    }
}
