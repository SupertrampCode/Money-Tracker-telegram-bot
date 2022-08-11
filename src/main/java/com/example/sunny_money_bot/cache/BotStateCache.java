package com.example.sunny_money_bot.cache;

import com.example.sunny_money_bot.enums.BotState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateCache {
        private final Map<Long, List<BotState>> botStateMap = new HashMap<>();

        public void saveBotState(Long userId, BotState botState) {
            if (botStateMap.containsKey(userId)){
                botStateMap.get(userId).add(botState);
            }else {
                botStateMap.put(userId, new ArrayList<>());
                botStateMap.get(userId).add(botState);
            }

        }
        public List<BotState> getUserBotStates (Long userId){
            return botStateMap.get(userId);
        }

        public BotState getLastUserBotState (Long userId){
            List<BotState> botStates = botStateMap.get(userId);
            int index = botStates.size()-1;
            return botStates.get(index);
        }
}
