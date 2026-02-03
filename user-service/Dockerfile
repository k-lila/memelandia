FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy AS final
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]