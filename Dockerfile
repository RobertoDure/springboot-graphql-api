FROM openjdk:17-jre-slim
VOLUME /tmp
COPY target/springboot-graphql-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]