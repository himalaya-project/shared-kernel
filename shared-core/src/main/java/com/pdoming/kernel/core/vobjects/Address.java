package com.pdoming.kernel.core.vobjects;

public class Address {

	private final String shippingAddress;

	public Address(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public static Address valueOf(String shippingAddress) {
		return new Address(shippingAddress);
	}
}
