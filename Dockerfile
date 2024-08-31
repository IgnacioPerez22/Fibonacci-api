FROM maven:3.8.7-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean install

EXPOSE 8080

CMD ["java", "-jar", "target/fibonacci-api-0.0.1-SNAPSHOT.jar"]
