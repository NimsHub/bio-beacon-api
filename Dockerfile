FROM eclipse-temurin:17-jre-alpine
COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar
WORKDIR /app
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]
