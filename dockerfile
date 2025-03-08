# Use an official OpenJDK base image
#FROM openjdk:17-jdk-slim
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/products-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
