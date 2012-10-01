package org.springframework.data.examples.repository;

import java.util.List;

import org.springframework.data.examples.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
	List<Contact> findByFirstname(String firstname);
	List<Contact> findByLastnameStartsWith(String lastname);
}
