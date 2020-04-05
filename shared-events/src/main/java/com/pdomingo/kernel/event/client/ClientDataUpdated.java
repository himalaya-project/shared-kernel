package com.pdomingo.kernel.event.client;

import com.pdoming.kernel.core.ddd.BaseDomainEvent;
import com.pdoming.kernel.core.ddd.Identifiable;

import java.time.Instant;

public abstract class ClientDataUpdated<ID extends Identifiable, T> extends BaseDomainEvent<ID> {

	protected Field updatedField;
	protected T updatedValue;

	public enum Field {
		NAME,
		EMAIL,
		PHONE_NUMBER,
		SHIPPING_ADDRESS
	}

	public ClientDataUpdated(ID sourceId, Field updatedField, T updatedValue) {
		super(sourceId, Instant.now());
		this.updatedField = updatedField;
		this.updatedValue = updatedValue;
	}

	public Field getUpdatedField() {
		return updatedField;
	}

	public T getUpdatedValue() {
		return updatedValue;
	}
}
