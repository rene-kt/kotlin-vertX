FROM openjdk:8-jre
FROM maven:3.6.3
MAINTAINER Renê Júnior
COPY . /var/www
WORKDIR /var/www
RUN mvn package
ENTRYPOINT mvn exec:java
EXPOSE 8888

