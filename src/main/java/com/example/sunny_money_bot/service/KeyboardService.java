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
        buttonNo.setText("No");
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
        everyMonthButton.setText("Every month");
        everyMonthButton.setCallbackData("everyMonth");
        InlineKeyboardButton everyYearButton = new InlineKeyboardButton();
        everyYearButton.setText("Every year");
        everyYearButton.setCallbackData("everyYear");
        keyboardMarkup.setKeyboard(inlineKeyboardOneButtonPerRow(
                everyDayButton,
                everyMonthButton,
                everyYearButton));
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup costPriorityKeyboard() {
        InlineKeyboardMarkup CPkeyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton firstPriorityButton = new InlineKeyboardButton();
        firstPriorityButton.setCallbackData("firstPriority");
        firstPriorityButton.setText("1");
        InlineKeyboardButton secondPriorityButton = new InlineKeyboardButton();
        secondPriorityButton.setCallbackData("secondPriority");
        secondPriorityButton.setText("2");
        InlineKeyboardButton thirdPriorityButton = new InlineKeyboardButton();
        thirdPriorityButton.setCallbackData("thirdPriority");
        thirdPriorityButton.setText("3");
        InlineKeyboardButton fourthPriorityButton = new InlineKeyboardButton();
        fourthPriorityButton.setCallbackData("fourthPriority");
        fourthPriorityButton.setText("4");
        InlineKeyboardButton fifthPriorityButton = new InlineKeyboardButton();
        fifthPriorityButton.setCallbackData("fifthPriority");
        fifthPriorityButton.setText("5");
        row.add(firstPriorityButton);
        row.add(secondPriorityButton);
        row.add(thirdPriorityButton);
        row.add(fourthPriorityButton);
        row.add(fifthPriorityButton);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);
        CPkeyboard.setKeyboard(rows);
        return CPkeyboard;
    }

    public InlineKeyboardMarkup timeIntervalOfReportKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton lastSevenDaysButton = new InlineKeyboardButton();
        lastSevenDaysButton.setText("Report for last 7 days");
        lastSevenDaysButton.setCallbackData("week");
        InlineKeyboardButton lastMonthButton = new InlineKeyboardButton();
        lastMonthButton.setText("Report for last 30 days");
        lastMonthButton.setCallbackData("month");
        InlineKeyboardButton lastThreeMonthsButton = new InlineKeyboardButton();
        lastThreeMonthsButton.setText("Report for last 90 days");
        lastThreeMonthsButton.setCallbackData("threeMonths");
        inlineKeyboardMarkup.setKeyboard(
                inlineKeyboardOneButtonPerRow(
                        lastSevenDaysButton,
                        lastMonthButton,
                        lastThreeMonthsButton));
        return inlineKeyboardMarkup;
    }

    public List<List<InlineKeyboardButton>> inlineKeyboardOneButtonPerRow(InlineKeyboardButton... buttons) {
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        for (InlineKeyboardButton button: buttons) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            rowsList.add(row);
        }
        return rowsList;
    }
}
