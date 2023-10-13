FROM openjdk
COPY /target/application-jarfile.jar application-jarfile.jar
CMD ["java",  "-jar", "/application-jarfile.jar"]
