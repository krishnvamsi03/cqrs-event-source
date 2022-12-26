package com.cqrs.account.cmd.api.commands;

import com.cqrs.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithDrawFundsCommand extends BaseCommand {
    private double withDrawAmount;
}
