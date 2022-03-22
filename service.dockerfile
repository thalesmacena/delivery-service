#Stage Build
FROM maven:3.8.3-openjdk-17 as build

ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean install -Dmaven.test.skip=true


#Stage Production
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/delivery-service.jar /app

ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

ENTRYPOINT ["sh", "-c", "/wait ; java $JAVA_OPTS -jar /app/delivery-service.jar"]