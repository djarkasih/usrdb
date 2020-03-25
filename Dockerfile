# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
FROM adoptopenjdk/openjdk13:alpine-jre

ARG JAR_FILE=target/*.jar
ARG APP_PORT="8080"

ENV JDBC_URL="jdbc:postgresql://pgsqldb:5432/caudb" \
    JDBC_USER="causradm" \
    JDBC_PASSWORD="c4usr4dmK3y"

COPY ${JAR_FILE} app.jar

EXPOSE ${APP_PORT}

CMD ["java","-jar","/app.jar"]