FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
COPY target/esng-referee.tar.gz /root/
WORKDIR /root
RUN tar -xzf esng-referee.tar.gz
CMD java -cp '/root/esng-referee/*' com.github.barasher.esng.Main

