package com.pdomingo.kernel.event.invoice;

import com.pdoming.kernel.core.ddd.BaseDomainEvent;
import com.pdoming.kernel.core.ddd.Identifiable;

import java.time.Instant;

public abstract class InvoiceCreated<ID extends Identifiable> extends BaseDomainEvent<ID> {

	public InvoiceCreated(ID sourceId) {
		super(sourceId, Instant.now());
	}
}
