package org.springframework.data.examples.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;

	private String number;
 
	private static final String PHONE_REGEX = "^(\\d{3}-){0,1}[2-9]\\d{2}-\\d{3}-\\d{4}$";
	private static final Pattern PATTERN = Pattern.compile(PHONE_REGEX);
	
	public static enum Type {MOBILE,OFFICE,HOME,FAX} 
	
	private Type type;
	
	public Phone(String number,Type type) {
		Assert.isTrue(isValid(number), "Invalid phone number");
		this.number = number;
		this.type = type;
	}

	/**
	 * Returns whether the given value is a valid {@link Phone}.
	 * 
	 * @param source must not be {@literal null} or empty.
	 * @return
	 */
	public static boolean isValid(String source) {
		Assert.hasText(source);
		return PATTERN.matcher(source).matches();
	}
    
	public String toString() {
		return number + "(" + type + ")";
	}
	
	@Component
	static class PhoneToStringConverter implements Converter<Phone, String> {

		/* 
		 * (non-Javadoc)
		 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
		 */
		@Override
		public String convert(Phone source) {
			return source == null ? null : source.toString();
		}
	}
	
	@Component
	static class StringToPhoneConverter implements Converter<String, Phone> {

		/*
		 * (non-Javadoc)
		 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
		 */
		public Phone convert(String source) {
			return StringUtils.hasText(source) ? new Phone(source,Phone.Type.OFFICE) : null;
		}
	}
	
	public Phone() {
        super();
    }

	public String getNumber() {
        return this.number;
    }

	public void setNumber(String number) {
        this.number = number;
    }

	public Type getType() {
        return this.type;
    }

	public void setType(Type type) {
        this.type = type;
    }
}
