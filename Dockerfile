FROM openjdk:17-jdk-alpine

WORKDIR /WareHouseApp

COPY target/WareHouseApp-0.0.1-SNAPSHOT.jar WareHouseApp.jar

EXPOSE 9988

CMD [ "java","-jar","WareHouseApp.jar" ]