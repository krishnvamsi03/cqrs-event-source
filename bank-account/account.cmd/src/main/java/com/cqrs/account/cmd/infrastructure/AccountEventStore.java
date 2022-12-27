package com.cqrs.account.cmd.infrastructure;

import com.cqrs.account.cmd.domain.AccountAggregate;
import com.cqrs.account.cmd.domain.EventStoreRepository;
import com.cqrs.cqrs.core.events.BaseEvent;
import com.cqrs.cqrs.core.events.EventModel;
import com.cqrs.cqrs.core.exceptions.AggregatoreNotFound;
import com.cqrs.cqrs.core.exceptions.ConcurrentException;
import com.cqrs.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, List<BaseEvent> events,
                           int expectedVersion) {
        var eventStream =
                eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && (eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion)) {
            throw new ConcurrentException("Expected version not set");
        }

        var version = expectedVersion;
        for (var event: events ) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .id(event.getId())
                    .timeStamp(new Date())
                    .eventData(event)
                    .eventType(event.getClass().getTypeName())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .build();
            var persistedStore = eventStoreRepository.save(eventModel);
            if (persistedStore != null) {

            }
        }

    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream =
                eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream != null || eventStream.size() == 0) {
            throw new AggregatoreNotFound();
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
