FROM eclipse-temurin:24-jdk-jammy
WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests

ENTRYPOINT ["java", "-jar", "target/api-0.9.1-SNAPSHOT.jar"]