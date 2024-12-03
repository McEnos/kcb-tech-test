FROM eclipse-temurin:21-jre-alpine

WORKDIR app
COPY target/*.jar application.jar

EXPOSE 8000


ENTRYPOINT ["java", "-jar", "application.jar"]