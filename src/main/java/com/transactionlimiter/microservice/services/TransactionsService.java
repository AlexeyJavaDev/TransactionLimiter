package com.transactionlimiter.microservice.services;

import com.transactionlimiter.microservice.dto.TransactionRequest;
import com.transactionlimiter.microservice.dto.TransactionResponse;
import com.transactionlimiter.microservice.models.Limit;
import com.transactionlimiter.microservice.models.Transaction;
import com.transactionlimiter.microservice.repositories.TransactionsRepository;
import com.transactionlimiter.microservice.util.LimitNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository, LimitsService limitsService, ModelMapper modelMapper) {
        this.transactionsRepository = transactionsRepository;
        this.limitsService = limitsService;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        String account = transaction.getAccountFromId();
        String category = transaction.getExpenseCategory();

        Limit limit = limitsService.findOne(account, category); // Ищем в БД лимит по аккаунту и категории

        if(limit == null)
        {
            limit = new Limit(new Date(), account, category);   // Если лимита нет, создаем новый, будет хранить баланс с начала месяца
            limitsService.save(limit);
        }
        transaction.setLimit(limit);    // Если лимит есть, присваиваем его транзакции

        double newLimitBalance = limit.getLimitBalance() - transaction.getTransactionSum(); // Вычисляем новый баланс лимита

        if(limit.getLimitSum() !=null && newLimitBalance < 0)   // Проверяем сумму транзакции на превышение лимита и сохраняем новую транзакцию
            transaction.setLimitExceed(true);
        transactionsRepository.save(transaction);

        limit.setLimitBalance(newLimitBalance); // Присваиваем новый баланс лимита и апдейтим его
        limitsService.save(limit);
    }
/*    public List<TransactionResponse> getTransactionsExceededLimit() {
        List<TransactionResponse> resultList = transactionsRepository.getTransactionsExceededLimit();
        if(resultList.isEmpty())
            throw new LimitNotFoundException();
        return transactionsRepository.getTransactionsExceededLimit();
    }*/
}
