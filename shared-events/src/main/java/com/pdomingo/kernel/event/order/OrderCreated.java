package com.pdomingo.kernel.event.order;

import com.pdoming.kernel.core.ddd.BaseDomainEvent;
import com.pdoming.kernel.core.ddd.Identifiable;

import java.time.Instant;

public abstract class OrderCreated<ID extends Identifiable> extends BaseDomainEvent<ID> {

	public OrderCreated(ID sourceId) {
		super(sourceId, Instant.now());
	}
}
