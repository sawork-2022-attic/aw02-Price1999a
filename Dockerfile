FROM openjdk:11
RUN apt-get update && apt-get install -y asciinema
COPY ./target/poshell-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app/
ENTRYPOINT ["/bin/bash"]