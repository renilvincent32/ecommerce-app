FROM openjdk:17-jdk-slim
MAINTAINER renilvincent
COPY target/ecommerce-app-backend-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]