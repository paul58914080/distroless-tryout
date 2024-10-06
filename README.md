# Distroless tryout

This is a sample project to try out distroless images. Here I have tried to create two distroless images for a [Java application](#java) and an [NGINX static website](#nginx-static).

## Java

Building the image

```shell
cd java
mvn clean install
docker build -t distroless-java -f .docker/distroless/Dockerfile .
```

Running the container

```shell
docker run distroless-java -p 8080:8080 --name distroless-java -d
```

## NGINX Static

Building the image

```shell
cd nginx-static
docker build -t distroless-nginx-static -f .docker/distroless/Dockerfile .
```

Running the container

```shell
docker run distroless-nginx-static -p 8080:80 --name distroless-nginx-static -d
```