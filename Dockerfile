FROM openjdk:8
EXPOSE 8080
MAINTAINER simagix@gmail.com
VOLUME /tmp
ADD build/libs/google-map-api-0.1.0.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
