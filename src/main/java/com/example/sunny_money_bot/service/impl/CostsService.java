package com.example.sunny_money_bot.service.impl;

import com.example.sunny_money_bot.entities.Costs;
import com.example.sunny_money_bot.repository.CostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CostsService {
    private final CostsRepository costsRepository;

    @Autowired
    public CostsService(CostsRepository costsRepository) {
        this.costsRepository = costsRepository;
    }

    public Optional<Costs> findById(Long id) {
        return costsRepository.findById(id);
    }

    public Collection<Costs> findAll(int pagination) {
        return costsRepository.findAll(PageRequest.of(0, pagination)).toList();
    }

    public void deleteByid(Long id) {
        costsRepository.deleteById(id);
    }

    public void save(Costs costs) {
        costsRepository.save(costs);
    }

    public boolean isExist(long id) {
        return costsRepository.findById(id).isPresent();
    }
}
