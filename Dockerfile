FROM openjdk:17-jdk-alpine

LABEL authors="alishanali"

WORKDIR /processorapp

COPY target/processorcontainer-0.0.1.jar processorapp.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "processorapp.jar"]