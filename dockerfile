# Es necesario ejecutar el comando
# mvn clean package -DskipTests
# para generar el jar

FROM openjdk:17-alpine
COPY target/proyectoDockerWeb-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java","-jar","java-app.jar"]