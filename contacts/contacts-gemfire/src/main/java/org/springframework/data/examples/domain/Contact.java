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
 */
package org.springframework.data.examples.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import javax.validation.constraints.NotNull;
import org.springframework.util.Assert;

@Region
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@NotNull
	private String firstname;
	
	@NotNull
	private String lastname;
	
	private Address billingAddress;
	private Address shippingAddress;
	private Set<Phone> phones = new HashSet<Phone>();
	private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

	public Contact() {
	}

	public Contact(long id, String firstname, String lastname) {
		this.id = id;
		Assert.hasText(firstname);
		Assert.hasText(lastname);

		this.firstname = firstname;
		this.lastname = lastname;
	}

	public void addEmailAddress(EmailAddress emailAddress) {
		Assert.notNull(emailAddress);
		this.emailAddresses.add(emailAddress);
	}

	public void addPhone(Phone phone) {
		Assert.notNull(phone);
		this.phones.add(phone);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Set<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<EmailAddress> getEmailAddresses() {
		return this.emailAddresses;
	}

	public void setEmailAddresses(Set<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public Address getBillingAddress() {
		return this.billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
