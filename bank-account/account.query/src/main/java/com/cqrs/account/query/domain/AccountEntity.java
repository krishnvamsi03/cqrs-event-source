package com.cqrs.account.query.domain;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.cqrs.core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AccountEntity extends BaseEntity {

    @Id
    private String id;
    private Date creationDate;
    private AccountType accountType;
    private String accountHolder;
    private double balance;
}
