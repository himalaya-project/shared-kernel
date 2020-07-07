package com.pdomingo.kernel.core.vobjects;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {

	private static final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

	private final String email;

	private Email(String email) {
		this.email = Objects.requireNonNull(email);

		if (!isValid(email)) {
			throw new InvalidEmailException("The provided email is not valid - " + email);
		}
	}

	public static Email valueOf(String email) {
		return new Email(email);
	}

	public static boolean isValid(String email) {
		return EMAIL_REGEX.matcher(email).matches();
	}

	public String domain() {
		return email.substring(email.indexOf("@"));
	}

	public String localPart() {
		return email.substring(0, email.indexOf("@"));
	}

	@Override
	public String toString() {
		return email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Email email1 = (Email) o;
		return email.equals(email1.email);
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}

	static class InvalidEmailException extends RuntimeException {
		public InvalidEmailException(String reason) {
			super(reason);
		}
	}
}
