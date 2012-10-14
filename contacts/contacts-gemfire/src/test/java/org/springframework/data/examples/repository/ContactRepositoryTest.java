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
package org.springframework.data.examples.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author David Turanski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/contacts-repository/applicationContext*.xml")
@ActiveProfiles("unit-test")
public class ContactRepositoryTest {
	@Autowired
	ConfigurableApplicationContext ctx;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	GemfireTemplate template;

	@Before
	public void setUp() {
		if (ctx.getEnvironment().acceptsProfiles("unit-test")) {

			for (long id = 1; id <= 5; id++) {
				Contact contact = new Contact(id, "firstname", "lastname");
				template.put(id, contact);
			}
		}
	}

	@Test
	public void testPaging() {
		Page<Contact> page = contactRepository.findAll(new PageRequest(0, 5));
		assertEquals(5, page.getSize());
		List<Contact> list = page.getContent();
		assertEquals(5, list.size());
	}

	@Test
	public void testFindOne() {
		Contact contact = contactRepository.findOne(3L);
		assertNotNull(contact);
		assertEquals(new Long(3L), contact.getId());
	}
}
