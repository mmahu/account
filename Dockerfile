FROM adoptopenjdk/openjdk11
ADD ./build/libs/*.jar /usr/src/app/
ADD ./docker.sh /docker.sh
CMD ["/bin/sh", "/docker.sh"]