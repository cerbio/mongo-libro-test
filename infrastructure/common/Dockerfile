#FROM alm-repos.sogei.it:8089/paas-base-image/openjdk-11:latest
FROM alm-repos.sogei.it:8082/paas-base-image/openjdk18-openshift-rhel7

WORKDIR /workspace/app

ARG JAR_FILE=./*.jar
ARG RUNTIME=./runtime

ENV AB_JOLOKIA_OFF=true
ENV WORKBENCH_MAX_METASPACE_SIZE=1024

ENV JAVA_OPTIONS="-XX:TieredStopAtLevel=1 -noverify"
USER root
ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY ${JAR_FILE} /deployments/
COPY ${RUNTIME} /deployments

RUN ln -sf /deployments/config /config

USER jboss



