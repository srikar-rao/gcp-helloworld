# Use a minimal base image
FROM eclipse-temurin:17-jre-alpine

# Set a non-root user for better security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set the working directory
WORKDIR /app

# Copy only the necessary JAR file
COPY target/gcp-helloworld-0.0.1-SNAPSHOT.jar /app/gcp-helloworld.jar

# Change ownership of the app directory to the non-root user
RUN chown -R appuser:appgroup /app

# Switch to the non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Set a more optimized JVM option
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/gcp-helloworld.jar"]
