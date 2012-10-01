package org.springframework.data.examples.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.examples.domain.Contact;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends GemfireRepository<Contact, Long>, CustomPagingRepository {
	List<Contact> findByFirstname(String firstname);
	List<Contact> findByLastnameStartsWith(String lastname);
	Page<Contact> findByFirstnameStartsWith(String firstname, Pageable pageable);
}
