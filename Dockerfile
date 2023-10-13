FROM openjdk:17-slim

COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar

RUN apt-get update && apt-get install -y python3 python3-pip && pip3 install pandas scikit-learn tensorflow

COPY src/main/resources/models app/models
COPY src/main/resources/scripts app/scripts
COPY src/main/resources/scalars app/scalars
COPY src/main/resources/data app/data
WORKDIR /app
RUN python3 --version
RUN pip3 --version

EXPOSE 5000

ENTRYPOINT ["java", "-jar","app.jar"]
