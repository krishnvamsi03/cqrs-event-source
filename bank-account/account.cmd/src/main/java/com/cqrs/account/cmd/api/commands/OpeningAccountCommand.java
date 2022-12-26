package com.cqrs.account.cmd.api.commands;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpeningAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingAmt;
}
