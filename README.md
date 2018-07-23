# PactJVMDemo
Pact JVM demo with Maven &amp; Spring boot

Branch Step01-FoldersPerService
-------------------------------

This branch contains 2 empty folders, one for each service:
* **Producer-UserManagement**: Service that manage users. It will be our producer.
* **Consumer-UI**: Service that manage the UI. It will be our consumer.

Branch Step02-EmptySpringBootProducer
-------------------------------
In this step we will create a new Spring boot sample project for our producer.
This can be done by downloading a template from https://start.spring.io/ 

Branch Step03-AddingRequestMappings
------------------------------------
In this step we will add 2 request mappings to our producer:

* **getAllUsers**: will return all the users. The path for this service is “/users”.
The producer will return a hard-coded list of users.
* **getUser**: will return a specific user. The path for this service is “/user/{id}”

Branch Step04–AddConsumerCode
---------------------------------------------

Branch name: Step04–AddConsumerCode

In this step we will add the consumer code. It is a CMD app that displays users information.

In order to run the services do the following: 

*Run the Producer* – run the main method in the class “UserManagementApplication”. This will run the service under localhost:8080.

Now, that the producer is up and running, you can start the UI (the consumer) by running the **UIApp** class.
It will show you textual menu in which you can select some operations. All the operations generate REST calls to the producer, get the response and analyze it.
In order to parse the response, the consumer uses the **'User'** class in order to deserialize the response from the producer.


Branch Step05-AddUITestsWithMock
---------------------------------------------

In this step we will add some component tests to the UI with regular Mock.
Testing the UI with regular mock is not good enough: 
* By using regular mock server, we assume we know what the producer sends us and we mock it.
* If the producer changes the API, we will not know it until we deploy both the consumer & producer code and test them together.

We are using Wire Mock in order to mock our producer.
The 3 tests that was added in this step are: 
* getNumOfUserTest
* getUsernamesTest
* getRoleTest

Each test executes a method of the consumer and validates the results. The consumer will perform the REST API calls to the mock instead of real producer.

Branch Step06-ChangeTheProducerApi
--------------------------------------------
In this step we are changing the API of the producer: We will return multiple roles per user instead of just one.

If we run the UI app, all API requests will fail because the UI expect the class **'User'** to contain one role, not a list.

BUT - We can see that when we run the consumer tests, they will all pass: the consumer doesn't know about that change.

This is the reason why we need Pact.

Branch Step07-AddingPactToConsumer
-----------------------------------
In this step we will remove the Wire Mock and add Pact to the consumer code.

We've added 2 **'@Pact'** methods - one for each API.
In each method we are describing the REST API: 
 - What is the request 
 - What is the response
 
 
This will do 2 things:
 - Create a mock at runtime for the UI tests
 - Create a pack file that can be run against the producer. The pact file is saved locally under the **'pacts'** directory in the project root folder. It is possible to use pack broker and store all the pact files in it.
 
 
 We've added the annotation **'@PactVerification'** to each **'@Test'** method.
 This will create the relevant mock before each test.
 
 
We are specifying the relevant pact method name in the **'fragment'** attribute of the **'@PactVerification'** annotation.
 
 We need to perform 2 changes in the consumer pom file:

 - Add pact dependency:
 ```xml
<dependency>
    <groupId>au.com.dius</groupId>
    <artifactId>pact-jvm-consumer-junit_2.12</artifactId>
    <version>3.5.19</version>
    <scope>test</scope>
</dependency>
```
 - Add system variable in the maven surefire plugin to specify the pact file location:
 ```xml
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.0</version>
    <configuration>
        <systemPropertyVariables>
            <pact.rootDir>${basedir}/../pacts</pact.rootDir>
        </systemPropertyVariables>
    </configuration>
</plugin>
 ```
 Branch Step08-PactVerifyOnProvider
 ----------------------------------
 In this step we will use the pact file to verify the provider.
 By doing so - we will be able to see that the verification is failed because the API is 
 broken between the consumer & provider.
 
 In order to verify the provider you need to do the following:
  * Add a plugin in the provider POM file to configure the host & port & pact folder:
  
  ```xml
  <plugin>
  				<groupId>au.com.dius</groupId>
  				<artifactId>pact-jvm-provider-maven_2.11</artifactId>
  				<version>3.5.10</version>
  				<configuration>
  					<serviceProviders>
  						<!-- You can define as many as you need, but each must have a unique name -->
  						<serviceProvider>
  							<name>UserManagement</name>
  							<!-- All the provider properties are optional, and have sensible defaults (shown below) -->
  							<protocol>http</protocol>
  							<host>localhost</host>
  							<port>8080</port>
  							<path>/</path>
  							<pactFileDirectory>${basedir}/../pacts</pactFileDirectory>
  						</serviceProvider>
  					</serviceProviders>
  					<pactBrokerUrl/>
  				</configuration>
  			</plugin>
  ```
  * Start the provider service.
  * Run the following command on the provider:
  
  *mvn pact:verify*
  
  What that command does is playing the pact file against the provider and validating the response.
  In our case it will fail because the provider will return list of roles and the pact tries to 
  verify it as a single String.
  
  Branch Step09-PactVerifyOnProviderAfterFixingCode
  -------------------------------------------------
  
  In this step we will revert the provider code to use one value role and run the *mvn pact:verify* command again.
  This time - the verification will pass.