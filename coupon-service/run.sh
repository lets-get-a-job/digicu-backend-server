#!/bin/sh
echo "******************************************************"
echo "Starting the Zuul Server"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar coupon_service.jar

