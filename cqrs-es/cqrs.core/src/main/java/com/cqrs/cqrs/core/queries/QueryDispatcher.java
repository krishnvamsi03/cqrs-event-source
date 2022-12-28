package com.cqrs.cqrs.core.queries;

import com.cqrs.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandle(Class<T> type, QueryHandlerMethod<T> handlerMethod);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
