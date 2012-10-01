package org.springframework.data.examples.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.examples.domain.Contact;

public interface CustomPagingRepository {
	public Iterable<Contact> findAll(Pageable pageable);
}
