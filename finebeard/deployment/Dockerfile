#Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17-al2023-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY /build/libs/finebeard.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]