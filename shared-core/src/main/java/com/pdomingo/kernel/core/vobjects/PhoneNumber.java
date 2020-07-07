package com.pdomingo.kernel.core.vobjects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public final class PhoneNumber {

	private static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();
	public static final String DEFAULT_COUNTRY = "ES";

	private final Phonenumber.PhoneNumber phoneNumber;

	private PhoneNumber(String phoneNumber) {
		try {
			this.phoneNumber = PHONE_UTIL.parse(phoneNumber, DEFAULT_COUNTRY);
		} catch (NumberParseException ex) {
			throw new InvalidPhoneNumber(ex);
		}
	}

	public static PhoneNumber valueOf(String phoneNumber) {
		return new PhoneNumber(phoneNumber);
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		try {
			return PHONE_UTIL.isValidNumber(PHONE_UTIL.parse(phoneNumber, DEFAULT_COUNTRY));
		} catch (NumberParseException e) {
			return false;
		}
	}

	public String rawPhoneNumber() {
		return PHONE_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
	}

	static class InvalidPhoneNumber extends RuntimeException {
		public InvalidPhoneNumber(Throwable ex) {
			super(ex);
		}
	}
}
