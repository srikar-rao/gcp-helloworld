FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gcp-helloworld.jar /app/gcp-helloworld.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/gcp-helloworld.jar"]
