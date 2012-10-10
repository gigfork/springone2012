/*
 * Copyright (c) 2012 by the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package org.springframework.data.examples.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@NotNull
	private String address1;

	private String address2;

	@NotNull
	private String city;

	private String state;

	@NotNull
	private String country;

	@NotNull
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
	public Address(String address1, String address2, String city, String state,
			String country, String postalCode) {

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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.address1)
				.append(this.address2 == null ? " " : " " + this.address2)
				.append(" ").append(this.city)
				.append(this.state == null ? " " : ", " + this.state)
				.append(", ").append(this.country).append(" ")
				.append(this.postalCode);
		return sb.toString();
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
