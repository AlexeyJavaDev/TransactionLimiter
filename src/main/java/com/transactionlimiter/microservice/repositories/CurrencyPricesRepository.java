package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.models.CurrencyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface CurrencyPricesRepository extends JpaRepository<CurrencyPrice, Date> {
    @Query("SELECT c FROM CurrencyPrice c ORDER BY c.datetime DESC LIMIT 1")
    Optional<CurrencyPrice> findActualPrice();
}
