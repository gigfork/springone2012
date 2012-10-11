springone2012
=============

Demo Code For SpringOne 2012 Presentation

Components
----------
- contacts-common : common utility classes
- contacts-data-loader: generate data, populate database, migrate to cache
- contacts-jpa: Spring Data JPA Repository implementation
- contacts-gemfire: Spring Data GemFire Repository implementation
- contacts-web: a Spring MVC web app, originally generated with Spring Roo

Configuration and Set Up
-------------------------
The current configuration requires a MySQL database server and at least one GemFire cache server with a GemFire locator.
All of this is configured in Spring.

To generate random data, run contacts-data-loader/..ContactsGenerator. In the current configuration, you need to create a 'contacts' database first.
Hibernate will auto-generate the schema. Alternately, you can use contacts-mysql.sql to create the schema.

Maven Build and Run
-------------------
	cd contacts
	mvn clean install
	cd contacts-web
	mvn tomcat:run -Dspring.data.repository=['jpa' or 'gemfire']

The system property will activate a corresponding maven profile to build and deploy with either contacts-jpa or contacts-gemfire


open your browser to [http://localhost/contacts-web/contacts]

To find by last name starts with use the request parameter 'last', e.g.,  [http://localhost/contacts-web/contacts?last=T]
To find by first name use the request parameter 'first', e.g.,  [http://localhost/contacts-web/contacts?first=David]
 

Also the system property is used by Spring to load the correct configuration.

STS Build and Run
-------------------
- select contacts-gemfire and open the context menu  Properties->Maven and type gemfire or jpa in the Active Maven Profiles. Press OK.
- edit src/main/resources/META-INF/spring/webApplicationContext.xml and edit the default (jpa or gemfire) value:

  		<import resource="classpath:/META-INF/spring/contacts-${spring.data.repository:jpa}/applicationContext*.xml"/>

- start a tcServer instance and deploy the app.  		
