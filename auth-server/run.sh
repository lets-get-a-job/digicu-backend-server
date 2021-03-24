#!/bin/sh
echo "******************************************************"
echo "Starting the Authentication Server"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar auth_server.jar

