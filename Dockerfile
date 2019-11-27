FROM jsurf/rpi-java
ADD ./build/libs/*.jar /usr/src/app/
ADD ./docker.sh /docker.sh
CMD ["/bin/sh", "/docker.sh"]