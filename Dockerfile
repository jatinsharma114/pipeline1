FROM openjdk
COPY /target/application-jarfile.jar application-jarfile.jar
EXPOSE 8082
CMD ["java",  "-jar", "/application-jarfile.jar"]
