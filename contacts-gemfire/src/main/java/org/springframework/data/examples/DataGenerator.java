
package org.springframework.data.examples;

import java.util.Date;
import java.util.Random;

import org.springframework.data.examples.domain.EmailAddress;
import org.springframework.data.examples.domain.Phone;
import org.springframework.util.StringUtils;

public class DataGenerator {


	private Random rand;
	private String[] firstNames;
	private String[] lastNames;
	private String[] emailDomains;

	public DataGenerator(String[] firstNames, String[] lastNames, String[] emailDomains) {
		rand = new Random(new Date().getTime());
		this.firstNames = firstNames;
		this.lastNames = lastNames;
		this.emailDomains = emailDomains;
	}

	public String randomFirstName() {
		return getRandomData(firstNames);
	}

	public String randomLastName() {
		return getRandomData(lastNames);
	}

	public EmailAddress randomEmailAddress(String firstName, String lastName) {

		lastName = lastName.replaceAll("\\W", "").toLowerCase();
		String principal = firstName.toLowerCase() + "." + lastName;
		
		boolean useLastname = rand.nextInt(10) == 0;

		String domain = useLastname ? lastName + ".com"
				: getRandomData(emailDomains);
		return new EmailAddress(principal + "@" + domain);
	}
	
	public EmailAddress companyEmailAddress(String firstName, String lastName, String companyName) {

		lastName = lastName.replaceAll("\\W", "").toLowerCase();
		
		String principal = firstName.toLowerCase().substring(0,1) +  lastName;
		
		companyName = companyName.replaceAll("\\W", "").toLowerCase();
		String domain = companyName + ".com";
		return new EmailAddress(principal + "@" + domain);
	}

	public Phone getRandomPhone() {
		 
		String countryCode = String.format("%03d", rand.nextInt(1000));
		
		String areaCode = String.format("%3d",rand.nextInt(800) + 200);
		
		String prefix = String.format("%3d",rand.nextInt(900) + 100);
		
		String number = String.format("%04d",rand.nextInt(1000));
		
		String phoneNumber = StringUtils.arrayToDelimitedString(new Object[]{countryCode,areaCode,prefix,number},"-");
		
		Phone.Type[] phoneTypes = new Phone.Type[]{ Phone.Type.FAX, Phone.Type.MOBILE, Phone.Type.OFFICE, Phone.Type.HOME};
		
		int i = rand.nextInt(phoneTypes.length);
		
		return new Phone(phoneNumber,phoneTypes[i]);
	}

	private String getRandomData(String[] data) {
		int i = rand.nextInt(data.length);
		return data[i];
	}
}
