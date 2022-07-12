package com.example.sunny_money_bot.handlers;

import com.example.sunny_money_bot.dao.UserDAO;
import com.example.sunny_money_bot.entities.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class EventHandler {

    private final UserDAO userDAO;

    public EventHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveNewUser(Message message, Long userId) {
        String userName = message.getFrom().getUserName();
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        userDAO.save(user);
    }


//    public BotApiMethod<?> saveNewIncome (Message message, Long userId, SendMessage sendMessage){
//        sendMessage.setChatId(String.valueOf(message.getChatId()));
//        sendMessage.setText("It will be a regular income?");
//    }
}
