package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.models.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitsRepository extends JpaRepository<Limit, Integer> {
    Optional<Limit> findFirstByAccountAndCategoryOrderBySettingDateDesc(String account, String category);   // Находим последний актуальный
    List<Limit> findAllByAccount(String account);
}
