FROM openjdk:latest
COPY ./target/seMethods-0.1.0.3-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "C:\Users\timho\IdeaProjects\Personal Learning\SEM_GroupProject\target\seMethods-1.0-SNAPSHOT.jar:"]