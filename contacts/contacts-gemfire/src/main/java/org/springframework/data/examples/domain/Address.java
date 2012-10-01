package org.springframework.data.examples.domain;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;


public class Address implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private String address1;

    private String address2;

    private String city;

    private String state;

    private String country;

    private String postalCode;
    
    public Address() {
    	
    }
    
    /**
	 * 
	 * @param address1
	 * @param address2
	 * @param city
	 * @param country
	 * @param postalcode
	 */
	public Address(String address1, String address2, String city, String state, String country, String postalCode) {

		Assert.hasText(address1, "Address line 1 must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(country, "Country must not be null or empty!");

		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getAddress1() {
        return this.address1;
    }

	public void setAddress1(String address1) {
        this.address1 = address1;
    }

	public String getAddress2() {
        return this.address2;
    }

	public void setAddress2(String address2) {
        this.address2 = address2;
    }

	public String getCity() {
        return this.city;
    }

	public void setCity(String city) {
        this.city = city;
    }

	public String getState() {
        return this.state;
    }

	public void setState(String state) {
        this.state = state;
    }

	public String getCountry() {
        return this.country;
    }

	public void setCountry(String country) {
        this.country = country;
    }

	public String getPostalCode() {
        return this.postalCode;
    }

	public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}