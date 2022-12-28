package com.cqrs.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpeningAccountCommand command);

    void handle(DepositFundsCommand command);

    void handle(WithDrawFundsCommand command);

    void handle(CloseAccountCommand command);
}
