package com.cqrs.account.query.api.queries;

import com.cqrs.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindByIdQuery extends BaseQuery {
    private String id;
}
