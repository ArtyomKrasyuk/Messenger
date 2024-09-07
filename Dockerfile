FROM openjdk:21-jdk-slim-buster
WORKDIR /app
COPY /target/Messenger-0.0.1-SNAPSHOT.jar /app/messenger.jar
ENTRYPOINT ["java", "-jar", "messenger.jar"]