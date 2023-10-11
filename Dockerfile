FROM eclipse-temurin:17-jre-alpine
COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar
COPY src/main/resources/models app/models
COPY src/main/resources/scripts app/scripts
COPY src/main/resources/scalars app/scalars
COPY src/main/resources/data app/data
COPY src/main/resources/requirements.txt app/requirements.txt
WORKDIR /app
RUN apk add --update --no-cache python3
RUN ln -sf python3 /usr/bin/python
RUN python3 -m ensurepip
RUN apk add py-pip
RUN pip -v
RUN pip3 install --no-cache --upgrade pip setuptools
RUN apk add build-base
RUN apk add python3-dev
RUN python3 -m venv env
RUN source env/bin/activate
RUN pip install -r requirements.txt
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]
