package com.cqrs.account.cmd.infrastructure;

import com.cqrs.cqrs.core.commands.BaseCommand;
import com.cqrs.cqrs.core.commands.CommandDispatcher;
import com.cqrs.cqrs.core.commands.CommandHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>,
            List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type,
                                                        CommandHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(type, e -> new LinkedList<>());
        handlers.add(handlerMethod);

    }

    @Override
    public void sendCommand(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0) {
            throw new IllegalArgumentException("No handler found for command "
                    + command.getClass());
        }

        if (handlers.size() > 1) {
            throw new RuntimeException("Multiple handler found for this " +
                    "command " + command.getClass());
        }
        handlers.get(0).handle(command);
    }
}
