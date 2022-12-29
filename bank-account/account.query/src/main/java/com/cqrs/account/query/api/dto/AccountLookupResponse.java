package com.cqrs.account.query.api.dto;

import com.cqrs.account.common.dto.BaseResponse;
import com.cqrs.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {
    private List<BaseEntity> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }
}
