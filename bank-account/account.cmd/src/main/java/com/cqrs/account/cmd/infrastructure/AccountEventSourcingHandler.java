package com.cqrs.account.cmd.infrastructure;

import com.cqrs.account.cmd.domain.AccountAggregate;
import com.cqrs.cqrs.core.domain.AggregatorRoot;
import com.cqrs.cqrs.core.handler.EventSourceHandler;
import com.cqrs.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourceHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregatorRoot aggregatorRoot) {
        eventStore.saveEvents(aggregatorRoot.getId(),
                aggregatorRoot.getUncommittedChanges(),
                aggregatorRoot.getVersion());
        aggregatorRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregator = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregator.replayEvents(events);
            var latestVersion =
                    events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregator.setVersion(latestVersion.get());
        }
        return aggregator;
    }
}
