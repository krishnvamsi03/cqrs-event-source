package com.cqrs.account.cmd.api.commands;

import com.cqrs.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    private String id;

    public CloseAccountCommand(String id) {
        super(id);
    }
}
