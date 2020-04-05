package com.pdoming.kernel.core.ddd;

import java.time.Instant;

public interface DomainEvent<ID extends Identifiable> {
	ID getSourceId();
	Instant getTimestamp();
}
