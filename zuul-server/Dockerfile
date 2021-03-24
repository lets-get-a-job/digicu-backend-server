FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN  apk update && apk upgrade && apk add netcat-openbsd
ARG JAR_FILE=target/*.jar
COPY run.sh .
ADD /target/*.jar zuul_server.jar
RUN chmod +x run.sh
CMD ./run.sh
