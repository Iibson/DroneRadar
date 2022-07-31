FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD DroneRadar-0.0.1-SNAPSHOT.jar crud.jar
ENTRYPOINT ["java","-jar","crud.jar"]