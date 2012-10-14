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

import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.examples.repository.ContactRepository;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.Region;
/**
 * Migrates data from a source repository to a GemFire Region
 * 
 * @author David Turanski
 *
 */
@Component
public class CacheLoader {
	/**
	 * Migrates data from RDBMS to Cache
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/spring/applicationContext.xml");
		CacheLoader cacheLoader = ctx.getBean(CacheLoader.class);
		cacheLoader.loadData();
	}
	
	
	private static Log log = LogFactory.getLog(CacheLoader.class);
	@Autowired 
	ContactRepository contactRepository;
	
	@Resource(name="Contact")
	Region<Long,Contact> contactRegion;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadData() {
		log.info("retrieving contacts from Source Repository...");
		Iterable<Contact> contacts = contactRepository.findAll();
		log.info("loading cache...");
	 
		for (Contact contact: contacts ) {
			contactRegion.remove(contact.getId());
			//Remove hibernate persistent set
			contact.setPhones(new HashSet(contact.getPhones()));
			contact.setEmailAddresses(new HashSet(contact.getEmailAddresses()));
			contactRegion.put(contact.getId(),contact);
		}
		log.info("loaded " + contactRepository.count()+ " items into cache");
	}

}
