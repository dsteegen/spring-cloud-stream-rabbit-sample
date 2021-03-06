# spring-cloud-stream-rabbit-sample
This repository contains a sample on how to use Spring Cloud Stream with RabbitMQ.

# Version info
The samples are running on Java 11 and are using the following frameworks:

* Spring Cloud Hoxton.SR8
* Spring Boot 2.3.3
* Spring Cloud Stream 3.0.8

# How To
To try out the samples, first spin up a RabbitMQ by executing the following docker command:

`docker run -p 15672:15672 -p 5672:5672 rabbitmq:3-management`

This will start RabbitMQ with the management interface and it will expose ports `5672` (RabbitMQ port) and `15672` (management port).

To verify that all is working, open a browser and navigate to [localhost:15672](http://localhost:15672). It should display the login page of the management interface.
Default credentials are `guest/guest`.

Next start both the `CloudStreamProducerApplication` and the `CloudStreamConsumerApplication`.

The producer application has a `/post` endpoint that takes a String as parameter. This parameter will be sent as a `Payload` object to RabbitMQ.
Try it out by invoking a POST on `localhost:8080/post/test`. You can use Postman, curl or use the included `post-message.http` that can be ran via Intellij. 
The server should respond with HTTP 200. Now when checking the logs of the consumer application, you should something similar to:

```
2020-09-06 16:52:17.289  INFO 16452 --- [Message-queue-1] b.d.t.c.CloudStreamConsumerApplication   : Received message CloudStreamConsumerApplication.Payload(message=test, time=2020-09-06T14:52:17.283380400Z)
```
