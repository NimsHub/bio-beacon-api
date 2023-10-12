FROM eclipse-temurin:17-jre-alpine
COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar
COPY src/main/resources/models app/models
COPY src/main/resources/scripts app/scripts
COPY src/main/resources/scalars app/scalars
COPY src/main/resources/data app/data
COPY src/main/resources/env app/env
COPY src/main/resources/requirements.txt app/requirements.txt
WORKDIR /app
RUN source env/bin/activate
RUN python3 --version
RUN pip3 --version
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]
