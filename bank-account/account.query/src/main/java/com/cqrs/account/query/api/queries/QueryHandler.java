package com.cqrs.account.query.api.queries;

import com.cqrs.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {

    List<BaseEntity> handle(FindByIdQuery query);

    List<BaseEntity> handle(FindByAccountHolderQuery query);

    List<BaseEntity> handle(FindAllAccountsQuery query);

    List<BaseEntity> handle(FindAccountByBalance query);
}
