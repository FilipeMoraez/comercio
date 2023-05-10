FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","/app.jar"]