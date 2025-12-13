FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN apt-get update && apt-get install -y sqlite3 curl git

CMD ["./mvnw", "spring-boot:run"]