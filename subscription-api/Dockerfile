FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG API_JAR=target/*.jar
COPY ${API_JAR} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]