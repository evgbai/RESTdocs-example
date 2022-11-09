# Spring REST "Dogs" Example

## Overview
* [Spring REST Docs](https://spring.io/projects/spring-restdocs) help document RESTful services.
* It combines handwritten documentation written with [Asciidoctor](https://asciidoctor.org/) and auto-generated snippets produced with [Spring MVC Test](https://github.com/evgbai/RESTdocs-example/blob/main/src/test/java/ru/decathlon/RESTdocs/example/controller/DogControllerTest.java).
* This helps create accurate, concise, and well-structured documentation.

### To run (via Docker)

Expose port 9090 from the image and access `RESTdocs-example` via the exposed port. You can then add and delete dogs as you see fit.

* #### Create Docker Image:
*Example*:

```
docker build -t restdocs-example/dogservice:unstable .
```

* #### Download Docker Image from DockerHub:
*Example*

```
docker pull evgbai/restdocs-example:unstable
docker run  --name restdocs-example -d -p 9090:9090 evgbai/restdocs-example:unstable
```

### Testing the server
After running your application or Docker container, you can navigate to http://localhost:9090/ to view the REST Docs documentation.