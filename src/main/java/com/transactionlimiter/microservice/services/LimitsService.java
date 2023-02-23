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
    public Limit findOne(String account, String category) { // Поиск в базе последнего установленного лимита по аккаунту и категории
        return limitsRepository.findFirstByAccountAndCategoryOrderBySettingDateDesc(account, category).orElse(null);
    }
    @Transactional  // ToDo проверить метод после добавления dto
    public long save(Limit limit) { // Сохраняем лимит в базу, метод для TransactionsService
        limitsRepository.save(limit);
        return limit.getId();
    }
    @Transactional
    public void set(LimitRequest limitRequest) {  // Клиент устанавливает новый лимит
        Limit settingLimit;
        Limit foundLimit = findOne(limitRequest.getAccount(), limitRequest.getCategory());    // Ищем в базе последний установленный этим клиентом лимит
        if(foundLimit == null) {    // Если вернулся null, то сохраняем новый
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setSettingDate(new Date());
            limitsRepository.save(settingLimit);
        } else if(foundLimit.getLimitSum() == null) {   // Иначе если лимит null (значит лимит создался автоматически при выполнении транзакции в прошлом)
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setLimitBalance(settingLimit.getLimitSum() + foundLimit.getLimitBalance());  // Уст баланс лимита (новый лимит плюс долг с начала месяца (он отрицательный))
            settingLimit.setSettingDate(new Date());
            limitsRepository.save(settingLimit);   // Сохраняем новый
        } else if(limitRequest.getLimitSum() == foundLimit.getLimitSum()) {    // Если новый лимит от пользователя равен старому, новый не создается
            throw new LimitAlreadyExistsException(); // Выбрасываем исключение о существовании такого лимита
        } else {
            settingLimit = modelMapper.map(limitRequest, Limit.class);
            settingLimit.setLimitBalance(settingLimit.getLimitSum() - (foundLimit.getLimitSum() - foundLimit.getLimitBalance()));     // Когда есть старые лимиты ->
            settingLimit.setSettingDate(new Date());                                                        // Устанавливаем баланс лимита, новый лимит минус остаток
            limitsRepository.save(settingLimit);
        }
    }
    public List<Limit> findAllByAccount(String account) {
        return limitsRepository.findAllByAccount(account);
    }
    public Optional<Limit> findOneActualByAccountAndCategory(String account, String category) {
        return limitsRepository.findFirstByAccountAndCategoryOrderBySettingDateDesc(account, category);
    }

    public List<Limit> findAllActualByAccountAndCategory(String account) {
        List<Limit> resultList = new ArrayList<>();
        limitsRepository.findFirstByAccountAndCategoryOrderBySettingDateDesc(account, "product").ifPresent(resultList::add);
        limitsRepository.findFirstByAccountAndCategoryOrderBySettingDateDesc(account, "service").ifPresent(resultList::add);
        return resultList;
    }
    public List<LimitResponse> toDTO(List<Limit> limits) {
        List<LimitResponse> resultList = limits.stream().filter(limit -> limit.getLimitSum() != null).map(limit -> modelMapper.map(limit, LimitResponse.class)).toList();
        if(resultList.isEmpty())
            throw new LimitNotFoundException();
        return resultList;
    }
}