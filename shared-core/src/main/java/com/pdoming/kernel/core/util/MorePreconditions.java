package com.pdoming.kernel.core.util;

public final class MorePreconditions {

	public static <T extends CharSequence> T checkNotEmptyOrNull(T charSequence, String fieldName) {
		if (charSequence == null) throw new NullPointerException("Field <"+fieldName+"> cannot be null");
		if (charSequence.length() == 0) throw new IllegalArgumentException("Field <"+fieldName+"> cannot be empty");
		return charSequence;
	}
}
