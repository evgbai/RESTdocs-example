FROM gradle:7.5.1-jdk17-alpine as builder
WORKDIR /temp
COPY . .
RUN gradle asciidoctor && gradle bootJar

FROM amazoncorretto:17-alpine3.16
WORKDIR /temp
ARG IMAGE_VERSION="1.0.0-SNAPSHOT"
ARG IMAGE_NAME="RESTdocs Example"
ARG MAINTAINER="Evgenii Bai firstName.lastName@mail.com"
LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}
COPY --from=builder /temp/build/libs/RESTdocs-example.jar /temp/RESTdocs-example.jar
ENTRYPOINT ["java", "-jar", "-Xms1024m", "-Xmx1024m", "/temp/RESTdocs-example.jar"]
EXPOSE 9090