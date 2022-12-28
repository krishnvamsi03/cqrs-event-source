package com.cqrs.cqrs.core.producers;

import com.cqrs.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent event);
}
