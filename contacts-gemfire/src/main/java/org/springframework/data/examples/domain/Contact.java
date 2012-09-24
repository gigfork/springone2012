package org.springframework.data.examples.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.util.Assert;

@Region
public class Contact implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private Long id;

    private String firstname;

    private String lastname;

    private Set<Phone> phones = new HashSet<Phone>();

    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    public Contact() {
    	
    }
    
	public Contact(String firstname, String lastname) {
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

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }
}
