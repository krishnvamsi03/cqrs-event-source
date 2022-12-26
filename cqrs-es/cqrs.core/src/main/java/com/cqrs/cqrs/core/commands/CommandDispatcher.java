package com.cqrs.cqrs.core.commands;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type,
                                                 CommandHandlerMethod<T> handlerMethod);

    void sendCommand(BaseCommand command);
}
