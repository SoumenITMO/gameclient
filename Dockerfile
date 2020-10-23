FROM openjdk:8
VOLUME /gameclient
EXPOSE 8083
ADD target/gameclient.jar gameclient.jar
ENTRYPOINT ["java","-jar","/gameclient.jar"]
