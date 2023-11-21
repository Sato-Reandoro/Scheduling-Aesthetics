FROM maven:3.9.5-jdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -X -DskipTests

# Path: Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build ./app/target/*.jar ./energylaser.jar
EXPOSE 8080
ENTRYPOINT java -jar energylaser.jar
