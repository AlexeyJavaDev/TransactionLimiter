package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.dto.TransactionResponse;
import com.transactionlimiter.microservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.transactionlimiter.microservice.dto.TransactionResponse" +
            "(t.accountToId, t.transactionCurrency, t.transactionSum, t.expenseCategory, t.transactionDate, l.settingLimitDate, l.limitSum) " +
            "FROM Transaction t JOIN t.limit l WHERE t.limitExceeded = true AND t.accountFromId = :account")
    public List<TransactionResponse> joinSqlTransaction(@Param("account") String account);  // find transactions that have exceeded the limit


}
