package com.cqrs.account.query.api.queries;

import com.cqrs.account.query.api.dto.EqualityType;
import com.cqrs.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByBalance extends BaseQuery {
    private EqualityType equalityType;
    private double amount;
}
