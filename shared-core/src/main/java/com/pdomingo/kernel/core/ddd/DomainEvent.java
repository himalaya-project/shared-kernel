package com.pdomingo.kernel.core.ddd;

import java.time.Instant;

public interface DomainEvent<ID extends Identifiable> {
	ID sourceId();
	Instant timestamp();
}
