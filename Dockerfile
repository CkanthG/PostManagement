FROM eclipse-temurin:11-jre-ubi9-minimal

EXPOSE 8080

COPY build/libs/*.jar /usr/local/lib/

CMD java -XX:MinRAMPercentage=40.0 -XX:MaxRAMPercentage=70.0 -XX:+UnlockExperimentalVMOptions -jar /usr/local/lib/*.jar
