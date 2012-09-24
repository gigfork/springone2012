package org.springframework.data.examples.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

@Entity
public class Contact {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<Phone>();

    @ManyToMany(cascade = CascadeType.ALL)
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

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

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
}
