# spring-boot-eshop

web service persistence state
-------------------------------
In this sample application, I am using in-memory H2 database.
For porting this application to stage  or production environments we would need a file based database such as mysql.
This can  that can be easily configured by adding the proper dependency and configuration settings There is no need to change the code.

REST API Documentation
----------------------
REST API documentation generated via swagger and available in http://localhost:8080/swagger-ui.html

Unit Tests
-----------
Not fully unit tested but i have tried to provide examples of tests on all levels, for instance for controllers and a services and plain unit tests for utility methods.

Running service locally using docker
------------------------------------
mvnw install dockerfile:build
#mvnw dockerfile:push
docker run -p 8080:8080 lbaxevanaki/eshop-web-service


Adding Authentication to the service
-------------------------------------

In order to add authentication to the web service I would pick OAuth2 protocol with JSON Web Tokens.
I would pick this way of authentication as it is purely stateless, enabling horizontal scaling without having the load balancers to direct to same box everytime.
Moreover, it is not necessary to store access tokens in a database. JSON Web Tokens (JWTs) are lightweight and can easily be used across platforms and languages.
It enables backends to accept requests simply by validating the contents of these JWTS.

Spring Boot provides support for this type of authentication, you can easily configure spring security, configure the authentication server and the recourse server.

Making the service redundant
----------------------------

One of the objectives of microservices is to allow independent deployment and scaling of applications. When we build a microservices solution using Spring Boot, 
both Spring Cloud and Kubernetes are options, as they provide components for resolving the most common challenges. 
I would choose Kubernetes as the main container manager and deployment platform for our solution, as it seems to be a technology supported by most of Cloud platforms together with docker.
We can still use Spring Cloud's interesting features mainly through the Spring Cloud Kubernetes project.

Spring Cloud Kubernetes provide Spring Cloud common interfaces implementations to consume Kubernetes native services, providing integration of Spring Cloud/Spring Boot applications running 
inside Kubernetes.

Kubernetes provides a resource named ConfigMap to externalize the parameters to pass to the application in the form of key-value pairs or embedded application.properties|yaml files. 
The Spring Cloud Kubernetes Config project makes Kubernetes `ConfigMap`s available during application bootstrapping and triggers hot reloading of beans or Spring context when changes 
are detected on observed `ConfigMap`s.


Making the service redundant we would need to put all the service instance behind a load balancer.Spring Cloud client applications calling a microservice should be interested on relying on a 
client load-balancing feature in order to automatically discover at which endpoint(s) it can reach a given service. This mechanism has been implemented within the Spring Cloud Kubernetes Ribbon
where a Kubernetes client will populate a Ribbon ServerList containing information about such endpoints.

Moreover, We would need to think about the persistence layer. Should we use a central/shared database or not? Is it possible to to somehow segregate  the data? By location or another way.
For authentication, an AUTH2 token based scheme would be appropriate.