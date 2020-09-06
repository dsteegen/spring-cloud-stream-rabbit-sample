package be.dsteegen.tutorials.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.Instant;

@SpringBootApplication
@EnableBinding(Sink.class)
@Slf4j
public class CloudStreamConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamConsumerApplication.class, args);
	}

	@Component
	static class MessageListener {

		@StreamListener(Sink.INPUT)
		void process(Message<Payload> message) {
			log.info("Received message {}", message.getPayload());
		}

	}

	@Data
	static class Payload {
		String message;
		Instant time;
	}
}
