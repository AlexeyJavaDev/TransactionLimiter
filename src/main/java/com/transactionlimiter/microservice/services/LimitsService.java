package com.transactionlimiter.microservice.services;

import com.transactionlimiter.microservice.dto.LimitRequest;
import com.transactionlimiter.microservice.models.Limit;
import com.transactionlimiter.microservice.dto.LimitResponse;
import com.transactionlimiter.microservice.repositories.LimitsRepository;
import com.transactionlimiter.microservice.util.LimitAlreadyExistsException;
import com.transactionlimiter.microservice.util.LimitNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LimitsService {
    private final LimitsRepository limitsRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public LimitsService(LimitsRepository limitsRepository, ModelMapper modelMapper) {
        this.limitsRepository = limitsRepository;
        this.modelMapper = modelMapper;
    }
    public Limit findOne(String account, String category) { // Find in db last set limit by account and category
        return limitsRepository.findFirstByAccountAndCategoryOrderBySettingLimitDateDesc(account, category).orElse(null);
    }
    @Transactional
    public long saveLimit(Limit limit) { // Сохраняем лимит в базу, метод для TransactionsService
        limitsRepository.save(limit);
        return limit.getId();
    }
    @Transactional
    public void setLimit(LimitRequest limitRequest) {  // User sets a new limit
        Limit settingLimit;
        Limit foundLimit = findOne(limitRequest.getAccount(), limitRequest.getCategory());    // Find in db last limit
        if(foundLimit == null) {    // If not found - create new
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setSettingLimitDate(new Date());
            limitsRepository.save(settingLimit);
        } else if(foundLimit.getLimitSum() == null) {   // If limitSum = null, set new limitBalance and save
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setLimitBalance(settingLimit.getLimitSum() + foundLimit.getLimitBalance());
            settingLimit.setSettingLimitDate(new Date());
            limitsRepository.save(settingLimit);
        } else if(limitRequest.getLimitSum() == foundLimit.getLimitSum()) {    // If new limit = last limit throw exception
            throw new LimitAlreadyExistsException();
        } else {
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setLimitBalance(settingLimit.getLimitSum() - (foundLimit.getLimitSum() - foundLimit.getLimitBalance()));
            settingLimit.setSettingLimitDate(new Date());
            limitsRepository.save(settingLimit);
        }
    }
    public List<Limit> findAllByAccount(String account) {
        return limitsRepository.findAllByAccount(account);
    }
    public Optional<Limit> findOneActualByAccountAndCategory(String account, String category) {
        return limitsRepository.findFirstByAccountAndCategoryOrderBySettingLimitDateDesc(account, category);
    }

    public List<Limit> findAllActualByAccountAndCategory(String account) {
        List<Limit> resultList = new ArrayList<>();
        limitsRepository.findFirstByAccountAndCategoryOrderBySettingLimitDateDesc(account, "product").ifPresent(resultList::add);
        limitsRepository.findFirstByAccountAndCategoryOrderBySettingLimitDateDesc(account, "service").ifPresent(resultList::add);
        return resultList;
    }
    public List<LimitResponse> toDTO(List<Limit> limits) {
        List<LimitResponse> resultList = limits.stream().filter(limit -> limit.getLimitSum() != null).map(limit -> modelMapper.map(limit, LimitResponse.class)).toList();
        if(resultList.isEmpty())
            throw new LimitNotFoundException();
        return resultList;
    }
}