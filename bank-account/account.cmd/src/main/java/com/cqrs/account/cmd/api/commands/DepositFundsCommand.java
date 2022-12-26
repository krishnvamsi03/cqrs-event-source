package com.cqrs.account.cmd.api.commands;

import com.cqrs.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double depositBalance;
}
