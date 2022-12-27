package com.cqrs.cqrs.core.handler;

import com.cqrs.cqrs.core.domain.AggregatorRoot;

public interface EventSourceHandler<T> {

    void save(AggregatorRoot aggregatorRoot);

    T getById(String id);
}
