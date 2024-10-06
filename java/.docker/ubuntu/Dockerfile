# Use the ubuntu with jdk base image
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY ../../java/bootstrap/target/bootstrap-exec.jar .

# Expose the application port
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "bootstrap-exec.jar"]