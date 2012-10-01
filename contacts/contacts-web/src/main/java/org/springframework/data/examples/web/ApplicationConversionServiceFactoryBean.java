package org.springframework.data.examples.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.examples.domain.Address;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.examples.repository.ContactRepository;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.util.StringUtils;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		registry.addConverter(getSetConverter());
		registry.addConverter(getAddressToStringConverter());
	}
	
	private Converter<Set<?>,String> getSetConverter() {
		return new Converter<Set<?>,String>() {

			@Override
			public String convert(Set<?> set) {
				
				return StringUtils.arrayToCommaDelimitedString(set.toArray());
			}
			
		};
	}

	@Autowired
    ContactRepository contactRepository;

	public Converter<Address, String> getAddressToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.springframework.data.examples.domain.Address, java.lang.String>() {
            public String convert(Address address) {
               return address.toString();
            }
        };
    }

	public Converter<String, Address> getStringToAddressConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.springframework.data.examples.domain.Address>() {
            public org.springframework.data.examples.domain.Address convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Address.class);
            }
        };
    }

	public Converter<Contact, String> getContactToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.springframework.data.examples.domain.Contact, java.lang.String>() {
            public String convert(Contact contact) {
                return new StringBuilder().append(contact.getFirstname()).append(' ').append(contact.getLastname()).toString();
            }
        };
    }

	public Converter<Long, Contact> getIdToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.springframework.data.examples.domain.Contact>() {
            public org.springframework.data.examples.domain.Contact convert(java.lang.Long id) {
                return contactRepository.findOne(id);
            }
        };
    }

	public Converter<String, Contact> getStringToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.springframework.data.examples.domain.Contact>() {
            public org.springframework.data.examples.domain.Contact convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Contact.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getContactToStringConverter());
        registry.addConverter(getIdToContactConverter());
        registry.addConverter(getStringToContactConverter());
        registry.addConverter(getStringToAddressConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
