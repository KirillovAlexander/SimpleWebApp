FROM openjdk:12-alpine

EXPOSE 8080

ADD target/SimpleWebApp-0.0.1-SNAPSHOT.jar SimpleWebApp.jar

ENTRYPOINT ["java","-jar","/SimpleWebApp.jar"]