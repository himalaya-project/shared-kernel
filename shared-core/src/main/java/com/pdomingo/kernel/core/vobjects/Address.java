package com.pdomingo.kernel.core.vobjects;

import com.pdomingo.kernel.core.util.MorePreconditions;

import java.util.Objects;

public final class Address {

	private final String street;
	private final String streetNumber;
	private final String city;
	private final String postalCode;
	private final String country;

	private Address(String street, String streetNumber, String city, String postalCode, String country) {
		this.street = MorePreconditions.checkNotEmptyOrNull(street, "street");
		this.streetNumber = MorePreconditions.checkNotEmptyOrNull(streetNumber, "streetNumber");
		this.city = MorePreconditions.checkNotEmptyOrNull(city, "city");
		this.postalCode = MorePreconditions.checkNotEmptyOrNull(postalCode, "postalCode");
		this.country = MorePreconditions.checkNotEmptyOrNull(country, "country");
	}
	
	public static Address.Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String street;
		private String streetNumber;
		private String city;
		private String postalCode;
		private String country;

		public Builder withStreet(String street) {
			this.street = street;
			return this;
		}

		public Builder withStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
			return this;
		}

		public Builder withCity(String city) {
			this.city = city;
			return this;
		}

		public Builder withPostalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public Builder withCountry(String country) {
			this.country = country;
			return this;
		}

		public Address build() {
			return new Address(street, streetNumber, city, postalCode, country);
		}
	}

	public String street() {
		return street;
	}

	public String streetNumber() {
		return streetNumber;
	}

	public String city() {
		return city;
	}

	public String postalCode() {
		return postalCode;
	}

	public String country() {
		return country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return streetNumber.equals(address.streetNumber) &&
				street.equals(address.street) &&
				city.equals(address.city) &&
				postalCode.equals(address.postalCode) &&
				country.equals(address.country);
	}

	@Override
	public int hashCode() {
		return Objects.hash(street, streetNumber, city, postalCode, country);
	}
}
