Contacts Demo
=============

Demo Code For SpringOne 2012 Presentation

# Modules

- contacts-common : common utility classes
- contacts-data-loader: generate data, populate database, migrate to cache
- contacts-jpa: Spring Data JPA Repository implementation
- contacts-gemfire: Spring Data GemFire Repository implementation
- contacts-web: a Spring MVC web app, originally generated with Spring Roo

# Configuration and Set Up

The current configuration requires a MySQL database server and at least one GemFire cache server with a GemFire locator.
All of this is configured in Spring.

To generate random data, run contacts-data-loader/..ContactsGenerator. In the current configuration, you need to create a 'contacts' database first.
Hibernate will auto-generate the schema. Alternately, you can use contacts-mysql.sql to create the schema.

To start a cache server and locator, it is convenient to use the [spring-gemfire-examples](https://github.com/SpringSource/spring-gemfire-examples) project. 

## A word about GemFire locators
-------------------------------------------------------------------------------------------------------------
Note that using a locator requires a GemFire installation. A limited development edition or full trial version 
may be downloaded from the [product home page](https://www.vmware.com/products/application-platform/vfabric-gemfire/overview.html).

Alternately, you can modify the application to connect directly to the server by editing [applicationContext-gemfire.xml](https://github.com/dturanski/springone2012/blob/master/contacts/contacts-gemfire/src/main/resources/META-INF/spring/contacts-gemfire/applicationContext-gemfire.xml).

Locate the following lines:

		<gfe:pool id="pool">
			<gfe:locator host="${locatorHost}" port="${locatorPort}" />
		</gfe:pool>

and change to:

		<gfe:pool id="pool">
			<gfe:server host="${serverHost}" port="40404" />
			<gfe:server host="${serverHost}" port="40405" />		
		</gfe:pool>
		
The above configuration will work with one or two cache servers. Also create a serverHost property, in the 'gemfireProperties' bean, 
e.g.,
 
 		<util:properties id="gemfireProperties">
 			...
			<prop key="serverHost">localhost</prop>
		</util:properties>

If you really want to go to town create properties for each of the server ports and replace the hard code values with placeholders.

-------------------------------------------------------------------------------------------------------------


Required GemFire components may be started using the provided gradle build script.  Clone the git repo and cd to the project directory.

To build the project from a terminal window:
		./gradlew build
	
To start the locator from a terminal window:
		./gradlew -q start-locator-10334
		
To start the cache server, use the provided [cache-config.xml](https://github.com/dturanski/springone2012/blob/master/contacts/cache-config.xml). Copy it to the spring-gemfire-examples directory and type:
		./gradlew -q run-generic-server -Pargs=cache-config.xml
		

# Maven Build and Run
	cd contacts
	mvn clean install
	cd contacts-web
	mvn tomcat:run -Dspring.data.repository=['jpa' or 'gemfire']

The system property will activate a corresponding maven profile to build and deploy with either contacts-jpa or contacts-gemfire


Also the system property is used by Spring to load the correct configuration.

# STS Build and Run
- import modules using Import -> Maven -> Existing Maven Projects
- select contacts-web and open the context menu: Properties->Maven and type gemfire or jpa in the Active Maven Profiles. Press OK.
- edit src/main/resources/META-INF/spring/webApplicationContext.xml and edit the default (jpa or gemfire) value:

  		<import resource="classpath:/META-INF/spring/contacts-${spring.data.repository:jpa}/applicationContext*.xml"/>

- deploy the contacts-web application a tcServer instance and start the instance

# Run the Demo

- open your browser to [http://localhost:8080/contacts-web/contacts](http://localhost:8080/contacts-web/contacts)

- To find by last name starts with use the request parameter 'last', e.g.,  [http://localhost:8080/contacts-web/contacts?last=T](http://localhost:8080/contacts-web/contacts?last=T)
- To find by first name use the request parameter 'first', e.g.,  [http://localhost:8080/contacts-web/contacts?first=David](http://localhost:8080/contacts-web/contacts?first=David)
 	
