package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.models.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitsRepository extends JpaRepository<Limit, Integer> {
    Optional<Limit> findFirstByAccountAndCategoryOrderBySettingLimitDateDesc(String account, String category);   // Find for actual
    List<Limit> findAllByAccount(String account);
}
