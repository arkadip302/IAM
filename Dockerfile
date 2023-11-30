FROM openjdk:22-jdk
ADD /target/iam-0.0.1-SNAPSHOT.jar iam-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/iam-0.0.1-SNAPSHOT.jar"]