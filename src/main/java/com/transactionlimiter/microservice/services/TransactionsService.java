package com.transactionlimiter.microservice.services;

import com.transactionlimiter.microservice.dto.TransactionRequest;
import com.transactionlimiter.microservice.dto.TransactionResponse;
import com.transactionlimiter.microservice.models.Limit;
import com.transactionlimiter.microservice.models.Transaction;
import com.transactionlimiter.microservice.repositories.TransactionsRepository;
import com.transactionlimiter.microservice.util.TransactionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final LimitsService limitsService;
    private final ModelMapper modelMapper;
    private final CurrencyPricesService currencyPricesService;
    private int limitBalanceMonth;
    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository, LimitsService limitsService, ModelMapper modelMapper, CurrencyPricesService currencyPricesService) {
        this.transactionsRepository = transactionsRepository;
        this.limitsService = limitsService;
        this.modelMapper = modelMapper;
        this.currencyPricesService = currencyPricesService;
        this.limitBalanceMonth = new Date().getMonth();
    }
    @Transactional
    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        String account = transaction.getAccountFromId();
        String category = transaction.getExpenseCategory();

        Limit limit = limitsService.findOne(account, category); // Find limit by account and category

        if(limit == null)
        {
            limit = new Limit(new Date(), account, category, limitBalanceMonth);   // If limit is not found - create new technical limit(with limitSum = null) to calculate limitBalance
            System.out.println(limitBalanceMonth + "     " + limit.getBalanceMonth());
            limitsService.saveLimit(limit);
        }
        transaction.setLimit(limit);    // If limit is found, set it to transaction

        double newLimitBalance;
        if(limit.getBalanceMonth() != limitBalanceMonth) {  // If balanceMonth not actual, update and reset limitBalance
            limit.setBalanceMonth(limitBalanceMonth);
            newLimitBalance = 0 - currencyPricesService.convertToUSD(transaction.getTransactionSum());
        }
        else
            newLimitBalance = limit.getLimitBalance() - currencyPricesService.convertToUSD(transaction.getTransactionSum()); // Calculate new limitBalance

        if(limit.getLimitSum() !=null && newLimitBalance < 0)   // Check limitSum for exceeding limit and save new transaction into db
            transaction.setLimitExceeded(true);
        transactionsRepository.save(transaction);

        limit.setLimitBalance(newLimitBalance); // Set new actual limitBalance and save changes
        limitsService.saveLimit(limit);
    }
    public List<TransactionResponse> getTransactionsExceededLimit(String account) { // Find transactions that have exceeded the limit
        List<TransactionResponse> resultList = transactionsRepository.joinSqlTransaction(account);
        if(resultList.isEmpty())
            throw new TransactionNotFoundException();
        return resultList;
    }
    @Scheduled(cron = "@monthly")
    public void updateMonth() {
        limitBalanceMonth = new Date().getMonth();
    }
}
