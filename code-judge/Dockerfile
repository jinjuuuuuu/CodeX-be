# Base image
FROM ubuntu:20.04

# Set non-interactive mode for apt-get
ENV DEBIAN_FRONTEND=noninteractive

# Update and install required packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    openjdk-17-jdk \
    gcc \
    g++ \
    curl \
    vim \
    build-essential

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Expose the application port
EXPOSE 8080

# Build and run the application
RUN ./mvnw package
CMD ["java", "-jar", "target/code-judge-0.0.1-SNAPSHOT.jar"]