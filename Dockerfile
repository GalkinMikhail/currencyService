FROM openjdk:11-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} currencyServiceApp.jar
ENTRYPOINT ["java", "-jar", "currencyServiceApp.jar"]