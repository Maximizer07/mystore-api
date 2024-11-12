FROM maven:3.8-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/mystore-api-0.0.1-SNAPSHOT.jar mystore-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mystore-api.jar"]