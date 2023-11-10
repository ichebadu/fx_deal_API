FROM openjdk:17-jdk-slim
COPY target/*.jar fx-deal.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fx-deal.jar"]