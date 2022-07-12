package com.example.sunny_money_bot.service;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class SendMessageService {

    public SendMessage createMessageWithReplyKeyboard (Long chatId,
                                                        String textMsg,
                                                        ReplyKeyboardMarkup replyKeyboardMarkup){
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMsg);
        if (replyKeyboardMarkup!=null){
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
    return sendMessage;}
}
