package org.springframework.data.examples.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

 
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class EmailAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
	
    private String value;
    
	/**
	 * Creates a new {@link EmailAddress} from the given {@link String} representation.
	 * 
	 * @param emailAddress must not be {@literal null} or empty.
	 */
	public EmailAddress(String emailAddress) {
		Assert.isTrue(isValid(emailAddress), "Invalid email address!");
		this.value = emailAddress;
	}
	
    public static boolean isValid(String source) {
		Assert.hasText(source);
		return PATTERN.matcher(source).matches();
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddress)) {
			return false;
		}

		EmailAddress that = (EmailAddress) obj;
		return this.value.equals(that.value);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Component
	static class EmailAddressToStringConverter implements Converter<EmailAddress, String> {

		/* 
		 * (non-Javadoc)
		 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
		 */
		@Override
		public String convert(EmailAddress source) {
			return source == null ? null : source.value;
		}
	}

	@Component
	static class StringToEmailAddressConverter implements Converter<String, EmailAddress> {
		/*
		 * (non-Javadoc)
		 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
		 */
		public EmailAddress convert(String source) {
			return StringUtils.hasText(source) ? new EmailAddress(source) : null;
		}
	}

	public String getValue() {
        return this.value;
    }

	public void setValue(String value) {
        this.value = value;
    }

	public EmailAddress() {
        super();
    }
}
