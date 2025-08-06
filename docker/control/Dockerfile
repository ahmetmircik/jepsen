# Use a multi-stage build to include debian-addons setup
FROM jgoerzen/debian-base-minimal:bookworm AS debian-addons
FROM jgoerzen/debian-base-standard:bookworm

# When running locally on macOS, uncomment the following:
# FROM --platform=linux/amd64 jgoerzen/debian-base-minimal:bookworm AS debian-addons
# FROM --platform=linux/amd64 jgoerzen/debian-base-standard:bookworm

# Copy setup files from debian-addons
COPY --from=debian-addons /usr/local/preinit/ /usr/local/preinit/
COPY --from=debian-addons /usr/local/bin/ /usr/local/bin/
COPY --from=debian-addons /usr/local/debian-base-setup/ /usr/local/debian-base-setup/

# Run setup scripts
RUN run-parts --exit-on-error --verbose /usr/local/debian-base-setup

# Set environment variables
ENV DEBIAN_FRONTEND=noninteractive \
    container=docker \
    LEIN_ROOT=true

STOPSIGNAL SIGRTMIN+3

# Set the GITHUB_TOKEN as a build argument
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

# Install packages in smaller groups to manage disk space
RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
        curl \
        git \
        wget \
    && apt-get clean && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/cache/apt/archives/*

# Install development tools
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        dos2unix \
        htop \
        iputils-ping \
        pssh \
        screen \
        maven \
    && apt-get clean && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/cache/apt/archives/*

# Install Java and related packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        libjna-java \
        openjdk-17-jdk-headless \
    && apt-get clean && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/cache/apt/archives/*

# Install larger packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        vim \
        emacs \
        gnuplot \
        graphviz \
    && apt-get clean && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/cache/apt/archives/*

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
RUN git clone --depth 1 --branch mx_chunk_size_log https://x-access-token:${GITHUB_TOKEN}@github.com/ahmetmircik/hazelcast-mono.git && \
    cd hazelcast-mono && \
    ./mvnw clean install -DskipTests -Dcheckstyle.skip -Pquick -q -pl '!hazelcast-enterprise-extensions/hazelcast-enterprise-cdc-db2-tests' && \
    cd .. && \
    rm -rf hazelcast-mono

# Setup initialization scripts
COPY ./bashrc /root/.bashrc
COPY ./init.sh /init.sh
RUN dos2unix /init.sh /root/.bashrc && \
    chmod +x /init.sh

CMD ["/init.sh"]