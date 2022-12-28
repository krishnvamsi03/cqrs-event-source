package com.cqrs.account.query.infrastructure;

import com.cqrs.cqrs.core.domain.BaseEntity;
import com.cqrs.cqrs.core.queries.BaseQuery;
import com.cqrs.cqrs.core.queries.QueryDispatcher;
import com.cqrs.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> map =
            new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandle(Class<T> type,
                                                     QueryHandlerMethod<T> handlerMethod) {
        var handler = map.computeIfAbsent(type, e -> new LinkedList<>());
        handler.add(handlerMethod);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handler = map.get(query.getClass());
        if (handler == null || handler.size() == 0) {
            throw new RuntimeException("No query handler registered");
        }
        if (handler.size() > 1) {
            throw new RuntimeException("cannot add more than handler");
        }
        return handler.get(0).handle(query);
    }
}
