package com.cqrs.account.query.infrastructure.consumer;

import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consumer(@Payload AccountOpenedEvent event, Acknowledgment ack);

    void consumer(@Payload FundsDepositedEvent event, Acknowledgment ack);

    void consumer(@Payload FundsWithdrawnEvent event, Acknowledgment ack);

    void consumer(@Payload AccountClosedEvent event, Acknowledgment ack);
}
