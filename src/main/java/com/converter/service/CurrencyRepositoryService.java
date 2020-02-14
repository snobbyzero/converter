package com.converter.service;

import com.converter.entity.Currency;
import com.converter.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyRepositoryService {
    private final CurrencyRepository currencyRepository;

    public CurrencyRepositoryService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> loadCurrencies() {
        return currencyRepository.findAll();
    }
}
