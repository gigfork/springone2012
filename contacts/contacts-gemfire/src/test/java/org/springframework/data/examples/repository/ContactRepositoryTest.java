package org.springframework.data.examples.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/applicationContext*.xml")
public class ContactRepositoryTest {
	@Autowired
	ContactRepository contactRepository;
	
	@Test
	public void testPaging() {
		contactRepository.findAll(new PageRequest(2, 50));
	}
}
