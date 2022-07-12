package com.example.sunny_money_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    public ReplyKeyboardMarkup mainMenuKeyboard() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("New income"));
        row2.add(new KeyboardButton("New cost"));
        row3.add(new KeyboardButton("Get report"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup yesNoInlineKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setText("Yes");
        buttonYes.setCallbackData("buttonYes");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonYes.setText("No");
        buttonNo.setCallbackData("buttonNo");
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        buttonsRow.add(buttonYes);
        buttonsRow.add(buttonNo);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonsRow);
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup frequencyKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton everyDayButton = new InlineKeyboardButton();
        everyDayButton.setText("Every day");
        everyDayButton.setCallbackData("everyDay");
        InlineKeyboardButton everyMonthButton = new InlineKeyboardButton();
        everyDayButton.setText("Every month");
        everyDayButton.setCallbackData("everyMonth");
        InlineKeyboardButton everyYearButton = new InlineKeyboardButton();
        everyDayButton.setText("Every year");
        everyDayButton.setCallbackData("everyYear");
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(everyDayButton);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(everyMonthButton);
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(everyYearButton);
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(row1);
        rowsList.add(row2);
        rowsList.add(row3);
        keyboardMarkup.setKeyboard(rowsList);
        return keyboardMarkup;
    }
}
