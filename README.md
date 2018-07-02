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
In this step we will add 1 request mappings to our producer:

**getAllUsers**: will return all the users. The path for this service is “/users”.
The producer will return a hard-coded list of users.

