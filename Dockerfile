FROM openjdk:17-jdk-alpine

LABEL authors="alishanali"

WORKDIR /processorapp

COPY target/processorcontainer-0.0.1.jar processorapp.jar
COPY src/main/resources/application.properties /processorapp/config/
EXPOSE 8000

ENTRYPOINT ["java", "-jar", "processorapp.jar","--spring.config.location=/processorapp/config/application.properties"]