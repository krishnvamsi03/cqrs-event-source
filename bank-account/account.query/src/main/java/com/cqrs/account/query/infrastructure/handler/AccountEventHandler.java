package com.cqrs.account.query.infrastructure.handler;

import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrawnEvent;
import com.cqrs.account.query.domain.AccountEntity;
import com.cqrs.account.query.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = AccountEntity.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(new Date())
                .accountType(event.getAccountType())
                .balance(event.getOpeningAmount())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var account = accountRepository.findById(event.getId());
        if (account.isEmpty()) {
            return;
        }
        var latestAmount = account.get().getBalance() + event.getAmount();
        account.get().setBalance(latestAmount);
        accountRepository.save(account.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var account = accountRepository.findById(event.getId());
        if (account.isEmpty()) {
            return;
        }
        var latestAmount = account.get().getBalance() - event.getAmount();
        account.get().setBalance(latestAmount);
        accountRepository.save(account.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
