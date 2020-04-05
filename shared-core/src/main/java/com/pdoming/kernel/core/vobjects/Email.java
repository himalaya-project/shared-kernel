package com.pdoming.kernel.core.vobjects;

/**
 *
 */
public class Email {

	private String email;

	public Email(String email) {
		this.email = email;
	}

	public static Email valueOf(String email) {
		return new Email(email);
	}

	public boolean isValid() {
		// TODO
		return true;
	}

	public void validate() throws InvalidEmailException {
		if (!isValid()) {
			throw new InvalidEmailException();
		}
	}

	static class InvalidEmailException extends RuntimeException {}
}
