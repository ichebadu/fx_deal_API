FROM openjdk:17
COPY target/*.jar /fx-deals-warehouse.jar
EXPOSE 8080
CMD ["java", "-jar", "fx-deals-warehouse.jar"]