package com.pdoming.kernel.core.vobjects;

public class PhoneNumber {

	private final String phoneNumber;

	public PhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static PhoneNumber valueOf(String phoneNumber) {
		return new PhoneNumber(phoneNumber);
	}

	public void validate() {
		// TODO
	}
}
