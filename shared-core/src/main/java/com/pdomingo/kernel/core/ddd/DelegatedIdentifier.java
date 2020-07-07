package com.pdomingo.kernel.core.ddd;

import java.util.Objects;

public abstract class DelegatedIdentifier<T> implements Identifiable {

	private final T value;

	public DelegatedIdentifier(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DelegatedIdentifier<?> that = (DelegatedIdentifier<?>) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
