package com.pdoming.kernel.core.ddd;

import java.time.Instant;

public abstract class BaseDomainEvent<ID extends Identifiable> implements DomainEvent<ID> {

	protected ID      sourceId;
	protected Instant timestamp;

	public BaseDomainEvent(ID sourceId, Instant timestamp) {
		this.sourceId = sourceId;
		this.timestamp = timestamp;
	}

	@Override
	public ID getSourceId() {
		return sourceId;
	}

	@Override
	public Instant getTimestamp() {
		return timestamp;
	}
}
