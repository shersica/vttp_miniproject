FROM openjdk:21-bookworm AS builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY mvnw.cmd .

RUN ./mvnw package -Dmvn.test.skip=true

FROM openjdk:21-bookworm

COPY --from=builder /src/target/miniproject-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost SPRING_REDIS_PORT=1234
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME=default SPRING_REDIS_PASSWORD=abc123


EXPOSE $PORT

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar