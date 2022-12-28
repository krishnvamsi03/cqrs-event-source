package com.cqrs.account.cmd.domain;

import com.cqrs.account.cmd.api.commands.DepositFundsCommand;
import com.cqrs.account.cmd.api.commands.OpeningAccountCommand;
import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrawnEvent;
import com.cqrs.cqrs.core.domain.AggregatorRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregatorRoot {
    private boolean isActive;
    private double balance;

    public AccountAggregate(OpeningAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .openingAmount(command.getOpeningAmt())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.balance = event.getOpeningAmount();
        this.isActive = true;
    }

    public void depositFunds(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Aggregator is not active");
        }

        if (amount <= 0) {
            throw new IllegalStateException("amount cannot be less than 1");
        }

        raiseEvent(FundsDepositedEvent.builder().id(this.id).amount(amount).build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance = event.getAmount();
    }

    public void withDrawFunds(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Aggregator is not active");
        }

        if (amount <= 0) {
            throw new IllegalStateException("amount cannot be less than 1");
        }

        raiseEvent(FundsWithdrawnEvent.builder().id(this.id).amount(amount).build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance = event.getAmount();
    }

    public void closeAccount() {
        if (!isActive) {
            throw new IllegalStateException("Aggregator is not active");
        }
        raiseEvent(AccountClosedEvent.builder().id(this.id).build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.isActive = false;
    }

    public double getBalance() {
        return this.balance;
    }
}
