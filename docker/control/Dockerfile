# Use a Debian-based image with necessary tools pre-installed
FROM jgoerzen/debian-base-standard:bookworm

# Set environment variables
ENV DEBIAN_FRONTEND=noninteractive \
    container=docker \
    LEIN_ROOT=true

STOPSIGNAL SIGRTMIN+3

# Set the GITHUB_TOKEN as a build argument
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

# Print GitHub token for debugging (consider removing in production)
RUN echo "===> docker file The GitHub token is: $GITHUB_TOKEN" && \
    echo "===> docker file The GitHub token is: ${GITHUB_TOKEN}"

# Install Jepsen dependencies and Git
RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
        curl \
        dos2unix \
        emacs \
        git \
        gnuplot \
        graphviz \
        htop \
        iputils-ping \
        libjna-java \
        openjdk-17-jdk-headless \
        pssh \
        screen \
        vim \
        wget \
        maven \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Leiningen
RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein && \
    mv lein /usr/bin && \
    chmod +x /usr/bin/lein && \
    lein self-install

# Copy and setup Jepsen
COPY jepsen/jepsen /jepsen/jepsen/
RUN if [ -f /jepsen/jepsen/project.clj ]; then \
      cd /jepsen/jepsen && \
      lein install && \
      lein deps; \
    fi
COPY jepsen /jepsen/

# Clone and build Hazelcast Mono repository
WORKDIR /app
RUN git clone --branch 6.0/cp/chunked https://${GITHUB_TOKEN}@github.com/ahmetmircik/hazelcast-mono.git && \
    cd hazelcast-mono && \
    ./mvnw clean install -DskipTests -Dcheckstyle.skip -Pquick -q -pl '!hazelcast-enterprise-extensions/hazelcast-enterprise-cdc-db2-tests'

# Setup initialization scripts
COPY ./bashrc /root/.bashrc
COPY ./init.sh /init.sh
RUN dos2unix /init.sh /root/.bashrc && \
    chmod +x /init.sh

CMD ["/init.sh"]