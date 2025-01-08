# Use a Debian-based image with necessary tools pre-installed
FROM jgoerzen/debian-base-standard:bookworm

ENV container=docker
STOPSIGNAL SIGRTMIN+3

ENV LEIN_ROOT true

# Set the GITHUB_TOKEN as a build argument or environment variable
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

RUN echo "===> The GitHub token is: $GITHUB_TOKEN"

# Jepsen dependencies and Git installation
RUN apt-get -qy update && \
    apt-get -qy install \
        curl dos2unix emacs git gnuplot graphviz htop iputils-ping libjna-java openjdk-17-jdk-headless pssh screen vim wget maven \
    && apt-get clean

RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein && \
    mv lein /usr/bin && \
    chmod +x /usr/bin/lein && \
    lein self-install

# Without --dev flag up.sh copies Jepsen to these subfolders
# With --dev flag they are empty until mounted
COPY jepsen/jepsen /jepsen/jepsen/
RUN if [ -f /jepsen/jepsen/project.clj ]; then \
      cd /jepsen/jepsen && \
      lein install && \
      lein deps; \
    fi
COPY jepsen /jepsen/

# Clone the Hazelcast Mono repository using GITHUB_TOKEN
WORKDIR /app
# RUN git clone --branch 6.0/cp/chunked https://${GITHUB_TOKEN}@github.com/ahmetmircik/hazelcast-mono.git
RUN git clone --branch 6.0/cp/chunked https://github.com/ahmetmircik/hazelcast-mono.git

# Change to the project directory and build with Maven Wrapper
WORKDIR /app/hazelcast-mono
RUN ./mvnw clean install -Dnot-quick -Pquick

# Additional initialization and setup
ADD ./bashrc /root/.bashrc
ADD ./init.sh /init.sh
RUN dos2unix /init.sh /root/.bashrc \
    && chmod +x /init.sh

CMD /init.sh
