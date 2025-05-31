FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=0 /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
