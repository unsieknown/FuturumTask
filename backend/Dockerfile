FROM eclipse-temurin:21-alpine
LABEL authors="GM"

WORKDIR /app

COPY target/futurum-task.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]