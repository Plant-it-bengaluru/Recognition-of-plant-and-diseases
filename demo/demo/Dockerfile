FROM openjdk
COPY ./target/demo-1.1.1.war ./demo.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.war"]