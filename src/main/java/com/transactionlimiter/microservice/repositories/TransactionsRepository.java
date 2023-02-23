package com.transactionlimiter.microservice.repositories;

import com.transactionlimiter.microservice.dto.TransactionResponse;
import com.transactionlimiter.microservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, String> {
/*    @Query("SELECT new com.transactionlimiter.microservice.dto.TransactionResponse (t.account_from t.account_to t.currency_shortname t.transaction_sum t.expense_category t.datetime l.setting_date l.limit_sum FROM transactions t JOIN t.limits l)")
    public List<TransactionResponse> getTransactionsExceededLimit();*/
}
