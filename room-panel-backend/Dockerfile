FROM gradle:6.7-jdk11-openj9 as GRADLE_BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean test && gradle clean bootJar

FROM openjdk:11.0.11-jre-slim
EXPOSE 8080
COPY --from=GRADLE_BUILD /home/gradle/src/build/libs/*.jar /app/smart-home-app.jar
CMD ["java", "-Dspring.profiles.active=local", "-jar", "/app/smart-home-app.jar"]

