package com.cqrs.account.common.events;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String id;
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingAmount;
}
