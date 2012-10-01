package org.springframework.data.examples.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddressTest {

	@Test
	public void test() {
		Address address = new Address("addr1","addr2","city","state","country","postal");
		System.out.println(address.toString());
	}

}
