package com.converter.service;

import com.converter.entity.Conversion;
import com.converter.entity.ExchangeRate;
import com.converter.entity.User;
import com.converter.repository.ExchangeRateRepository;
import com.converter.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Date;

@Service
public class ConvService {
    private ExchangeRateRepository exchangeRateRepository;
    private UserRepository userRepository;
    private final XmlService xmlService;

    public ConvService(ExchangeRateRepository exchangeRateRepository, UserRepository userRepository, XmlService xmlService) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.userRepository = userRepository;
        this.xmlService = xmlService;
    }


    public Float calculate(Conversion conversion, String username) {
        Date date = xmlService.getDate();
        if (exchangeRateRepository.findAllByDate(date).isEmpty()) {
            xmlService.getCurrencies("http://www.cbr.ru/scripts/XML_daily.asp");
        }
        ExchangeRate exchangeRate1 = exchangeRateRepository.findByDateAndCurrencyCharCode(date,conversion.getFirstCharCode()).orElseThrow();
        ExchangeRate exchangeRate2 = exchangeRateRepository.findByDateAndCurrencyCharCode(date,conversion.getSecondCharCode()).orElseThrow();

        Float value1 = exchangeRate1.getValue();
        Float value2 = exchangeRate2.getValue();
        Float nominal1 = exchangeRate1.getCurrency().getNominal();
        Float nominal2 = exchangeRate2.getCurrency().getNominal();

        Float result = conversion.getEnteredValue() * (value1 / nominal1) /
                (value2 / nominal2);

        conversion.setResult(result);

        User user = this.findUserByUsername(username);

        user.addConversion(conversion);
        conversion.setUser(user);
        userRepository.save(user);
        return result;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
