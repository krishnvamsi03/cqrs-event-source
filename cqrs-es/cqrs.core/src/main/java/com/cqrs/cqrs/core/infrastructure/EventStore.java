package com.cqrs.cqrs.core.infrastructure;

import com.cqrs.cqrs.core.events.BaseEvent;

import java.util.Iterator;
import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, List<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);

    List<String> getAggregateIds();
}
