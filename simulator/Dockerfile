FROM maven:3.8-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
WORKDIR ./
COPY --from=build /home/app/target/simulator-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/simulator.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/simulator.jar"]