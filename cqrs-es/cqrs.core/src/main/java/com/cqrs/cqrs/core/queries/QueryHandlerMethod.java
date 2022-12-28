package com.cqrs.cqrs.core.queries;

import com.cqrs.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery> {

    List<BaseEntity> handle(T query);
}
