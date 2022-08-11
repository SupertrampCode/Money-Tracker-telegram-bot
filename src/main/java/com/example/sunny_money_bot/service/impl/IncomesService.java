package com.example.sunny_money_bot.service.impl;

import com.example.sunny_money_bot.entities.Income;
import com.example.sunny_money_bot.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class IncomesService {
    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomesService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public Collection<Income> findAll(int pagination) {
        return incomeRepository.findAll(PageRequest.of(0, pagination)).toList();
    }

    public void deleteByid(Long id) {
        incomeRepository.deleteById(id);
    }

    public void save(Income income) {
        incomeRepository.save(income);
    }

    public boolean isExist(long id) {
        return incomeRepository.findById(id).isPresent();
    }

}
