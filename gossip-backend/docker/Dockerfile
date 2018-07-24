FROM anapsix/alpine-java

WORKDIR /app

ADD ./src/main/resources/application.yml /app
ADD ./build/libs/gossip-backend-1.0-all.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/gossip-backend-1.0-all.jar", "server", "/app/application.yml"]