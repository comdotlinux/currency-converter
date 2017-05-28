FROM openjdk:8
ENV WORKDIR /usr/src/cc
ENV DEPLOYMENT_DIR ${WORKDIR}/app
ENV MAVEN_VERSION 3.5.0

RUN mkdir -pv ${DEPLOYMENT_DIR}

COPY . ${DEPLOYMENT_DIR}


RUN curl -L -O http://artfiles.org/apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip \
    && unzip apache-maven-${MAVEN_VERSION}-bin.zip -d ${WORKDIR} \
    && rm apache-maven-${MAVEN_VERSION}-bin.zip

WORKDIR ${DEPLOYMENT_DIR}
RUN ${WORKDIR}/apache-maven-${MAVEN_VERSION}/bin/mvn clean install
EXPOSE 8080
ENTRYPOINT java -jar target/currency-converter.jar

# OLD Dockerfile
#FROM store/oracle/serverjre:8
#COPY target/currency-converter.jar .
#EXPOSE 8080
#ENTRYPOINT java -jar ./currency-converter.jar