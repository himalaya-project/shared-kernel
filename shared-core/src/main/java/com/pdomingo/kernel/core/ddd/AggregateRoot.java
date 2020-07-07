package com.pdomingo.kernel.core.ddd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public abstract class AggregateRoot<ID extends Identifiable> {

	public abstract ID id();

	protected Collection<DomainEvent<ID>> eventLog;

	public AggregateRoot() {
		this.eventLog = new ArrayList<>(2);
	}

	public Stream<DomainEvent<ID>> eventLog() {
		return eventLog.stream();
	};

	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) return true;
		if (that == null || getClass() != that.getClass()) return false;
		AggregateRoot<ID> thatAgg = (AggregateRoot<ID>) that;
		return this.id().equals(thatAgg.id());
	}
}
