package com.cqrs.account.query.api.queries;

import com.cqrs.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindByAccountHolderQuery extends BaseQuery {
    private String holder;
}
