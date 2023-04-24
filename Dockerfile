FROM openjdk:11

WORKDIR /app
COPY ./target/spotify-backend-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "spotify-backend-0.0.1-SNAPSHOT.jar"]