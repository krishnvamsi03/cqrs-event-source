package com.cqrs.account.query.infrastructure.handler;

import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);
}
