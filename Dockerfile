FROM maven:3.3-jdk-8

add * /appli/

RUN cd /appli/ && \
    mvn clean install

ENTRYPOINT ["/appli/entrypoint.sh"]
