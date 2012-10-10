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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.examples.domain.Address;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.examples.domain.EmailAddress;
import org.springframework.data.examples.domain.Phone;
import org.springframework.data.examples.repository.ContactRepository;
import org.springframework.data.examples.util.ResourceUtils;

/**
 * Generates random Contact data
 * 
 * @author David Turanski
 * 
 */
public class ContactsGenerator {

	private static int NUM_CONTACTS = 10000;
	private final static String FIRST_NAMES_FILE = "/Firstnames.txt";
	private final static String LAST_NAMES_FILE = "/Lastnames.txt";
	private final static String EMAIL_DOMAINS_FILE = "/Domains.txt";

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/spring/contacts-jpa/applicationContext*.xml");

		ContactRepository contactRepository = context
				.getBean(ContactRepository.class);

		System.out.println("there are " + contactRepository.count()
				+ " contacts in the repository");

		String[] firstNames = loadData(FIRST_NAMES_FILE);
		String[] lastNames = loadData(LAST_NAMES_FILE);
		String[] emailDomains = loadData(EMAIL_DOMAINS_FILE);

		DataGenerator dataGenerator = new DataGenerator(firstNames, lastNames,
				emailDomains);

		for (long i = 1; i <= NUM_CONTACTS; i++) {
			String firstname = dataGenerator.randomFirstName();
			String lastname = dataGenerator.randomLastName();
			Contact contact = new Contact(i, firstname, lastname);

			Address billingAddress = new Address("123 " + firstname + " "
					+ lastname + " Street", null, "City", "State", "Country",
					"12345");
			Address shippingAddress = new Address("345 " + firstname + " "
					+ lastname + " Street", null, "City", "State", "Country",
					"34567");

			contact.setBillingAddress(billingAddress);
			contact.setShippingAddress(shippingAddress);

			Set<EmailAddress> uniqueEmailAddresses = new HashSet<EmailAddress>();

			for (int j = 1; j <= 3; j++) {

				EmailAddress emailAddress = dataGenerator.randomEmailAddress(
						firstname, lastname);

				while (uniqueEmailAddresses.contains(emailAddress)) {
					emailAddress = dataGenerator.randomEmailAddress(firstname,
							lastname);
				}

				uniqueEmailAddresses.add(emailAddress);

				Phone phone = dataGenerator.getRandomPhone();
				contact.addEmailAddress(emailAddress);
				contact.addPhone(phone);
			}
			System.out.println("saving " + contact.getFirstname() + " "
					+ contact.getLastname());
			contactRepository.save(contact);
		}
	}

	private static String[] loadData(String fileName) {
		String[] names = null;
		try {
			String data = ResourceUtils.classPathResourceAsString(
					ContactsGenerator.class, fileName);
			names = data.split("\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return names;
	}
}
