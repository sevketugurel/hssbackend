# Multi-stage build for HSS Backend
FROM eclipse-temurin:21-jdk-alpine AS build

# Set working directory
WORKDIR /workspace/app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Install necessary packages
RUN apk add --no-cache curl

# Create app user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Change ownership to app user
RUN chown -R appuser:appgroup /app

# Switch to app user
USER appuser

# Expose port
EXPOSE 8090

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8090/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
