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
The 2 tests that was added in this step are: 
* getNumOfUserTest
* getUsernamesTest

The tests executes 2 methods of the consumer and validates the results. The consumer will perform the REST API calls to the mock instead of real producer.

Branch Step06-ChangeTheProducerApi
--------------------------------------------
In this step we are changing the API of the producer: We will return multiple roles per user instead of just one.

If we run the UI app, all API requests will fail because the UI expect the class **'User'** to contain one role, not a list.

BUT - We can see that when we run the consumer tests, they will all pass: the consumer doesn't know about that change.

This is the reason why we need Pact.

Branch Step07-AddingPactToConsumer
-----------------------------------
In this step we will remove the Wire Mock and add Pact to the consumer code.