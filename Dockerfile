FROM maven:3.9.7-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . /app

RUN [ "mvn", "clean", "package" ]

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/blog-0.0.1-SNAPSHOT.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=prod

CMD [ "java", "-jar", "/app/app.jar" ]

EXPOSE 8080