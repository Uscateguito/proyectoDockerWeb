FROM openjdk:17-alpine
COPY target/proyecto-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java","-jar","java-app.jar"]