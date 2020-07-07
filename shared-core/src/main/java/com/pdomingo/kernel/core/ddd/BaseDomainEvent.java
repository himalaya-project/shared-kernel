package com.pdomingo.kernel.core.ddd;

import java.time.Instant;

public abstract class BaseDomainEvent<ID extends Identifiable> implements DomainEvent<ID> {

	protected ID      sourceId;
	protected Instant timestamp;

	public BaseDomainEvent(ID sourceId, Instant timestamp) {
		this.sourceId = sourceId;
		this.timestamp = timestamp;
	}

	@Override
	public ID sourceId() {
		return sourceId;
	}

	@Override
	public Instant timestamp() {
		return timestamp;
	}
}
