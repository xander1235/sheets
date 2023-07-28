FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /app

COPY . .

RUN mvn clean install 

FROM openjdk:slim-buster

ENV JAR_FILE sheet-*SNAPSHOT.jar
ENV RESOURCES src/main/resources

RUN mkdir /usr/share/triffy

ENV PROJECT_HOME /usr/share/triffy

EXPOSE 8080

COPY --from=builder /app/target/$JAR_FILE $PROJECT_HOME/

COPY --from=builder /app/$RESOURCES/application.properties $PROJECT_HOME/
COPY --from=builder /app/$RESOURCES/credentials.json $PROJECT_HOME/

WORKDIR $PROJECT_HOME

CMD [ "sh", "-c", "java ${JVM} -jar $PROJECT_HOME/$JAR_FILE --spring.config.location=$PROJECT_HOME/application.properties"]
