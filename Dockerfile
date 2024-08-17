FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/gcp-helloworld-0.0.1-SNAPSHOT.jar /app/gcp-helloworld.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/gcp-helloworld.jar"]
