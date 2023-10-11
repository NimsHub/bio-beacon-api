FROM eclipse-temurin:17-jre-alpine
COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar
COPY src/main/resources/models app/models
COPY src/main/resources/scripts app/scripts
COPY src/main/resources/scalars app/scalars
COPY src/main/resources/data app/data
WORKDIR /app
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]
