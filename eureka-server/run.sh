#!/bin/sh
echo "******************************************************"
echo "Starting the Eureka Server"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar eureka_server.jar

