FROM frolvlad/alpine-oraclejdk8:slim
#FIXME add variable for version
VOLUME /tmp
COPY target/extremestartupng-referee-*.tar.gz /root/
WORKDIR /root
RUN tar -xzf extremestartupng-referee-*.tar.gz
CMD java -cp '/root/extremestartupng-referee-1.0-SNAPSHOT/*' com.barasher.esng.Main

