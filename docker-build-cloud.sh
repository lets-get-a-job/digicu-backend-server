group="digicu"
service=${1}

if [ "${service}" == "all" ] || [ "${service}" == "eurekaserver" ]
then
  echo "eurekaserver build..."
  mvn -pl eureka-server clean
  mvn -pl eureka-server package -DskipTests
  sudo docker build -t ${group}/eurekaserver eureka-server/.
  echo "eurekaserver done..."
fi
if [ "${service}" == "all" ] || [ "${service}" == "zuulserver" ]
then
  echo "zuulserver build..."
  mvn -pl zuul-server clean
  mvn -pl zuul-server package -DskipTests
  sudo docker build -t ${group}/zuulserver zuul-server/.
  echo "zuulserver done..."
fi
if [ "${service}" == "all" ] || [ "${service}" == "couponservice" ]
then
  echo "couponservice build..."
  mvn -pl coupon-service clean
  mvn -pl coupon-service package -DskipTests
  sudo docker build -t ${group}/couponservice coupon-service/.
  echo "couponservice done..."


fi
if [ "${service}" == "all" ] || [ "${service}" == "userservice" ]
then
  echo "userservice build..."
  sudo docker build -t ${group}/userservice user-service/.
  echo "userservice done..."
fi

if [ "${service}" == "all" ] || [ "${service}" == "posservice" ]
then
  echo "posservice build..."
  sudo docker build -t ${group}/posservice pos-service/.
  echo "posservice done..."
fi

if [ "${service}" == "all" ] || [ "${service}" == "qrservice" ]
then
  echo "qrservice build..."
  sudo docker build -t ${group}/qrservice qr-service/.
  echo "qrservice done..."
fi