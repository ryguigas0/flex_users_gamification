FROM openjdk:23-slim

WORKDIR /

COPY pom.xml /pom.xml
COPY mvnw /mvnw
COPY .mvn /.mvn
COPY src /src
COPY target /target

RUN ./mvnw install package

ENTRYPOINT [ "java", "-jar", "/target/app.war" ]
