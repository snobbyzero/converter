package com.converter.repository;

import com.converter.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByDateAndCurrencyCharCode(Date date, String charCode);
    List<ExchangeRate> findAllByDate(Date date);
}
