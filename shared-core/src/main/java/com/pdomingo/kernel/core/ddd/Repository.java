package com.pdomingo.kernel.core.ddd;

import java.util.Optional;

public interface Repository<AGG extends AggregateRoot<ID>, ID extends Identifiable> {

	Optional<AGG> findById(ID id);
}
