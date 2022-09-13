package com.example.sunny_money_bot.service;

import com.example.sunny_money_bot.entities.Costs;
import com.example.sunny_money_bot.entities.Income;
import com.example.sunny_money_bot.repository.CostsRepository;
import com.example.sunny_money_bot.repository.IncomeRepository;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

  private final IncomeRepository incomeRepository;

  private final CostsRepository costsRepository;

  public ReportService(IncomeRepository incomeRepository, CostsRepository costsRepository) {
    this.incomeRepository = incomeRepository;
    this.costsRepository = costsRepository;
  }

  public String reportForTimeInterval(String timeInterval) {
    LocalDateTime timeEnd = LocalDateTime.now();
    LocalDateTime timeStart;
    List<Income> incomes;
    List<Costs> costs;
    switch (timeInterval) {
      case "week":
        timeStart = timeEnd.minusDays(7);
        incomes = incomeRepository.getAllByTimeBetween(timeStart, timeEnd);
        costs = costsRepository.getAllByTimeBetween(timeStart, timeEnd);
        break;
      case "month":
        timeStart = timeEnd.minusDays(30);
        incomes = incomeRepository.getAllByTimeBetween(timeStart, timeEnd);
        costs = costsRepository.getAllByTimeBetween(timeStart, timeEnd);
        break;
      default:
        timeStart = timeEnd.minusDays(90);
        incomes = incomeRepository.getAllByTimeBetween(timeStart, timeEnd);
        costs = costsRepository.getAllByTimeBetween(timeStart, timeEnd);
    }
    StringBuilder reportMsg = new StringBuilder();
    reportMsg.append("Here is your transactions per " + timeInterval + ".");
    reportMsg.append("\n \n Incomes:");
    for (Income income : incomes) {
      String incomeDateParsedByPattern = income.getTime()
          .format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));
      reportMsg.append(
          "\n" + income.getReason() + " - " + income.getSum() + "\n" + incomeDateParsedByPattern);
    }
    reportMsg.append("\n \n Costs:");
    for (Costs cost : costs) {
      String costDateParsedByPattern = cost.getTime()
          .format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));
      reportMsg.append(
          "\n" + cost.getReason() + " - " + cost.getSum() + "\n" + costDateParsedByPattern);
    }
    return reportMsg.toString();
  }
}
