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

/**
 * 
 * @author David Turanski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/contacts-repository/applicationContext*.xml")
@ActiveProfiles("unit-test")
public class ContactIntegrationTest {

	@Autowired
	ContactRepository contactRepository;

	@Test
	@Transactional
	public void testCreateContact() {
		Contact contact = new Contact(1L, "David", "Turanski");
		contact.addPhone(new Phone("610-555-1212", Phone.Type.MOBILE));
		contact.addEmailAddress(new EmailAddress("dturanski@dturanski.com"));
		contact.setBillingAddress(new Address("123 David Street", null, "City", "PA", "US", "19312"));
		Contact saved = contactRepository.save(contact);

		assertEquals(1, contactRepository.count());
//TODO: implement equals() on domain objects
		contact.setVersion(0);
		assertEquals(contact.toString(), contactRepository.findOne(saved.getId()).toString());
		
		contactRepository.delete(saved);

		assertEquals(0, contactRepository.count());
	}

}
