FROM openjdk
COPY /target/application.jar application.jar 
CMD ["java",  "-jar", "application.jar"]
