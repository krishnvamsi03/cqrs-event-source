package com.cqrs.account.query.api.queries;

import com.cqrs.account.query.api.dto.EqualityType;
import com.cqrs.account.query.domain.AccountEntity;
import com.cqrs.account.query.domain.AccountRepository;
import com.cqrs.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandler implements QueryHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindByIdQuery query) {
        Optional<AccountEntity> bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> res = new LinkedList<>();
        res.add(bankAccount.get());
        return res;
    }

    @Override
    public List<BaseEntity> handle(FindByAccountHolderQuery query) {
        Optional<AccountEntity> bankAccount =
                accountRepository.findByAccountHolder(query.getHolder());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> res = new LinkedList<>();
        res.add(bankAccount.get());
        return res;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<AccountEntity> allAccount = accountRepository.findAll();
        List<BaseEntity> baseEntities = new LinkedList<>();
        allAccount.forEach(baseEntities::add);
        return baseEntities;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByBalance query) {
        if (query.getEqualityType() == EqualityType.LESS_THAN) {
            return accountRepository.findByBalanceLessThan(query.getAmount());
        } else {
            return accountRepository.findByBalanceGreaterThan(query.getAmount());
        }
    }
}
