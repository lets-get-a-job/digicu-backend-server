group="digicu"
service=${1}

if [ "${service}" == "all" ] || [ "${service}" == "eurekaserver" ]
then
  echo "eurekaserver build..."
  mvn -pl eureka-server clean
  mvn -pl eureka-server package -DskipTests
  docker build -t ${group}/eurekaserver eureka-server/.
  echo "eurekaserver done..."
fi
if [ "${service}" == "all" ] || [ "${service}" == "zuulserver" ]
then
  echo "zuulserver build..."
  mvn -pl zuul-server clean
  mvn -pl zuul-server package -DskipTests
  docker build -t ${group}/zuulserver zuul-server/.
  echo "zuulserver done..."
fi

##################
if [ $# -eq 2 ]; then
  if [ "${2}" == "up"]; then
    docker-compose -f docker/docker-compose.yml up
  fi
fi