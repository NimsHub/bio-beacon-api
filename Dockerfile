FROM openjdk:17
COPY target/bio-beacon-0.0.1-SNAPSHOT.jar app/app.jar
COPY src/main/resources/models app/models
COPY src/main/resources/scripts app/scripts
COPY src/main/resources/scalars app/scalars
COPY src/main/resources/data app/data
COPY src/main/resources/requirements.txt app/requirements.txt
WORKDIR /app
RUN apk add --update --no-cache python3
RUN python3 -m ensurepip
RUN python3 -m venv env
RUN source env/bin/activate
RUN apk add py-pip
RUN python3 --version
RUN pip3 install --no-cache --upgrade pip setuptools
RUN pip3 --version
RUN apk add --no-cache --virtual .build-deps \
		gnupg \
		tar \
		xz \
		\
		bluez-dev \
		bzip2-dev \
		dpkg-dev dpkg \
		expat-dev \
		findutils \
		gcc \
		gdbm-dev \
		libc-dev \
		libffi-dev \
		libnsl-dev \
		libtirpc-dev \
		linux-headers \
		make \
		ncurses-dev \
		openssl-dev \
		pax-utils \
		readline-dev \
		sqlite-dev \
		tcl-dev \
		tk \
		tk-dev \
		util-linux-dev \
		xz-dev \
		zlib-dev \
	;
RUN apk add build-base
RUN apk add python3-dev
RUN pip3 install -r requirements.txt
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]
