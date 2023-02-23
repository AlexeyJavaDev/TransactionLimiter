package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, String> {

}
