FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./certificates /tmp/certifdockicates
ENV certsfolder=/tmp/certificates
ENV truststore=${JAVA_HOME}/jre/lib/security/cacerts
ENV storepassword=changeit

COPY "./target/ms-recipe-0.0.1.jar" /opt/