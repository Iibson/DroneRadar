FROM openjdk:17
WORKDIR ./
ADD ./target/simulator-1.0-SNAPSHOT-jar-with-dependencies.jar crud.jar
ENTRYPOINT ["java","-jar","crud.jar"]