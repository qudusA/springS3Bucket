FROM maven:3.9.2-eclipse-temurin:17-alpine as build
COPY ./src src/
COPY ./pom.xml pom.xml/

RUN maven clean package -DskipTests

FROM  eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
COPY --from=builder target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8880