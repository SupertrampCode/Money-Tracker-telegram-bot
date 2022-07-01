package com.example.sunny_money_bot.cache;

import com.example.sunny_money_bot.entities.User;
import com.example.sunny_money_bot.enums.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotCache implements Cache<User>{
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public void add(User user) {
        Long userId = user.getId();
        if (userId!=null){
            userMap.put(userId,user);
        }
    }

    @Override
    public void remove(User user) {
        userMap.remove(user.getId(),user);
    }

    @Override
    public User findById(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }
}
