FROM gradle:6.7-jdk11-openj9 as GRADLE_BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN adduser --disabled-password build_user && chown -R build_user /home/gradle/src
USER build_user
RUN gradle clean test && gradle clean bootJar

FROM openjdk:11.0.11-jre-slim
EXPOSE 8080
COPY --from=GRADLE_BUILD /home/gradle/src/build/libs/*.jar /app/smart-home-app.jar
RUN adduser --disabled-password run_user && chown -R run_user /app
USER run_user
WORKDIR /app
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-Dspring.config.location=/app/resources/application.properties", "-jar", "/app/smart-home-app.jar"]

