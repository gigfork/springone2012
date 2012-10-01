package org.springframework.data.examples;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.examples.domain.Address;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.examples.domain.EmailAddress;
import org.springframework.data.examples.domain.Phone;
import org.springframework.data.examples.repository.ContactRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/contacts-jpa/applicationContext*.xml")
@ActiveProfiles("unit-test")
public class ContactIntegrationTest {
	
	@Autowired 
	ContactRepository contactRepository;
	
	@Test
	@Transactional
	public void testCreateContact() {
		Contact contact = new Contact("David","Turanski");
		contact.addPhone(new Phone("610-555-1212", Phone.Type.MOBILE));
		contact.addEmailAddress(new EmailAddress("dturanski@dturanski.com"));
		contact.setBillingAddress(new Address("123 David Street",null,"City","PA","US","19312"));
		Contact saved = contactRepository.save(contact);
		
		assertEquals(1, contactRepository.count());
		
		assertEquals(contact, contactRepository.findOne(saved.getId()));
		
		contactRepository.delete(saved);
		
		assertEquals(0, contactRepository.count());
	}

}
