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
 */package org.springframework.data.examples.repository;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.bp.edu.emory.mathcs.backport.java.util.Collections;
import com.gemstone.gemfire.cache.query.SelectResults;

/**
 * Implement Custom paging in Gemfire for Contacts
 * @author David Turanski
 *
 */
public class ContactRepositoryImpl implements CustomPagingRepository {

	@Autowired
	GemfireTemplate template;

	@Override
	public Page<Contact> findAll(Pageable pageable) {
		long first = pageable.getOffset() + 1;
		long last = pageable.getOffset() + pageable.getPageSize();
		SelectResults<Contact> results = template.find("select * from /Contact where id >= $1 and id <= $2", first,
				last);
		
		List<Contact> contacts = results.asList();
		
		Collections.sort(contacts, new Comparator<Contact>(){

			@Override
			public int compare(Contact c0, Contact c1)  {
				return (int)(c0.getId() - c1.getId());
			}
			
		});
		
		return results == null ? null : new PageImpl<Contact>(contacts, pageable, results.size());
	}

}
