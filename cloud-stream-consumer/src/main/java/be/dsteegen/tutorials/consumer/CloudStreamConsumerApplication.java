package be.dsteegen.tutorials.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class CloudStreamConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamConsumerApplication.class, args);
	}

	@Bean
	Consumer<Payload> messageInput() {
		return payload -> {
			if (payload.message.equalsIgnoreCase("error")) {
				throw new RuntimeException("System failure!");
			}
			log.info("Received message {}", payload);
		};
	}

	@Data
	static class Payload {
		String message;
		Instant time;
	}
}
