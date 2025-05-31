FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# SQLite3のインストール
RUN apt-get update && apt-get install -y sqlite3

# データディレクトリの作成
RUN mkdir -p /app/data

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
