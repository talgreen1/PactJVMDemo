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
In order to parse the response, the consumer uses the **'User'** class in order to de-serilize the response from the producer.


