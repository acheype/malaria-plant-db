FROM java:8-jdk
MAINTAINER Adrien Cheype <adrien.cheype@ird.fr>

RUN mkdir /opt/malariaplantdb
COPY ./malariaplantdb.war /opt/malariaplantdb/

ENTRYPOINT ["/opt/malariaplantdb/malariaplantdb.war", "--spring.profiles.active=prod"]
EXPOSE 80
