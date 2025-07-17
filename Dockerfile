FROM openjdk:21-jdk-slim

WORKDIR /app

COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

COPY target/Task-Management-System-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
