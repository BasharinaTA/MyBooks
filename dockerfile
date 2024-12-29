FROM openjdk:17-oracle
#ADD . /src
#WORKDIR /src
#RUN ./mvnw package -DskipTests
ARG JAR_FILE=target2/*.jar
COPY ${JAR_FILE} MyBooks-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/MyBooks-0.0.1-SNAPSHOT.jar"]
