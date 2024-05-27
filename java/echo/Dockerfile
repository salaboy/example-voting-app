FROM docker.io/library/eclipse-temurin:21-jdk-jammy AS builder
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM docker.io/library/eclipse-temurin:21-jre-jammy
RUN useradd spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/extracted/dependencies/ ./
COPY --from=builder workspace/extracted/spring-boot-loader/ ./
COPY --from=builder workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder workspace/extracted/application/ ./
ENTRYPOINT ["java", "-jar", "app.jar"]