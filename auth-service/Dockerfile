FROM eclipse-temurin:17-jre

WORKDIR /app

ARG JAR_FILE=target/auth-service-1.0.0.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
