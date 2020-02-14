package com.converter.repository;

import com.converter.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByNumCode(Integer numCode);
    Optional<Currency> findByCharCode(String charCode);
}
