package com.cqrs.account.cmd.api.commands;

import com.cqrs.account.cmd.domain.AccountAggregate;
import com.cqrs.cqrs.core.handler.EventSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourceHandler<AccountAggregate> eventSourceHandler;

    @Override
    public void handle(OpeningAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        aggregate.depositFunds(command.getDepositBalance());
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(WithDrawFundsCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        if (command.getWithDrawAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Insufficient balance");
        }
        aggregate.withDrawFunds(command.getWithDrawAmount());
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDBCommand command) {
        eventSourceHandler.republishEvents();
    }
}
