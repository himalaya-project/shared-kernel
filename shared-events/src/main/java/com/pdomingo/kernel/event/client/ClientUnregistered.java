package com.pdomingo.kernel.event.client;

import com.pdoming.kernel.core.ddd.BaseDomainEvent;
import com.pdoming.kernel.core.ddd.DomainEvent;
import com.pdoming.kernel.core.ddd.Identifiable;

import java.time.Instant;

public abstract class ClientUnregistered<ID extends Identifiable> extends BaseDomainEvent<ID> {

	public ClientUnregistered(ID sourceId, Instant timestamp) {
		super(sourceId, timestamp);
	}
}
