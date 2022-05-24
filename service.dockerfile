#Stage Build
FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app

# Download the dependencies
COPY pom.xml .

RUN mvn verify clean --fail-never

# Build the project
COPY ./src ./src

RUN mvn package -Dmaven.test.skip=true


#Stage Production
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/delivery-service.jar /app

ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

ENTRYPOINT ["sh", "-c", "/wait ; java $JAVA_OPTS -jar /app/delivery-service.jar"]