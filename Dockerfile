FROM openjdk:17
EXPOSE 8080
WORKDIR /app
COPY  ./target/vaccination-management-0.0.1-SNAPSHOT.jar  ./
CMD ["java","-jar","vaccination-management-0.0.1-SNAPSHOT.jar"]

## Build stage
#FROM maven:3.9.3 AS build
#WORKDIR /app
#COPY . /app
#RUN mvn clean package -DskipTestsdock
#
##
## Package stage
#FROM openjdk:17
#COPY ./target/vaccination-management-0.0.1-SNAPSHOT.jar ./
## ENV PORT=8080
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","vaccination-management-0.0.1-SNAPSHOT.jar"]