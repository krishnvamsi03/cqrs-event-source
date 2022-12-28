package com.cqrs.account.common.events;

import com.cqrs.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
