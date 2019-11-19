FROM java:8
EXPOSE 8080
ADD /target/eshop-web-service-0.0.1-SNAPSHOT.jar eshop-web-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "eshop-web-service-0.0.1-SNAPSHOT.jar"]