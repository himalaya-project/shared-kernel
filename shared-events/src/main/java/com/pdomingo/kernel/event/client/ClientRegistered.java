package com.pdomingo.kernel.event.client;

import com.pdoming.kernel.core.ddd.BaseDomainEvent;
import com.pdoming.kernel.core.ddd.Identifiable;
import com.pdoming.kernel.core.vobjects.Address;
import com.pdoming.kernel.core.vobjects.Email;
import com.pdoming.kernel.core.vobjects.PhoneNumber;

import java.time.Instant;

public abstract class ClientRegistered<ID extends Identifiable> extends BaseDomainEvent<ID> {

	protected Email       email;
	protected PhoneNumber phoneNumber;
	protected Address     shippingAddress;

	public ClientRegistered(ID sourceId, Instant timestamp, Email email, PhoneNumber phoneNumber, Address shippingAddress) {
		super(sourceId, timestamp);
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.shippingAddress = shippingAddress;
	}

	public Email getEmail() {
		return email;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}
}
