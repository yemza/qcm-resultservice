FROM openjdk:17-jdk-slim
MAINTAINER qcm.com
EXPOSE 8888
COPY target/resultservice-0.0.1-SNAPSHOT.jar resultservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java" ,"-jar" , "resultservice-0.0.1-SNAPSHOT.jar" ]