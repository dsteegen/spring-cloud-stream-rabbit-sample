package be.dsteegen.tutorials.producer;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.function.Supplier;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@SpringBootApplication
public class CloudStreamProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamProducerApplication.class, args);
	}

	@RestController
    @RequiredArgsConstructor
    class ProducerController {

		private final MessageHandler messageHandler;

		@PostMapping("/post/{message}")
		void postOnQueue(@PathVariable String message) {
            var payload = withPayload(new Payload(message)).build();
            messageHandler.processor.onNext(payload);
		}

	}

	@Value
	class Payload {
		String message;
		Instant time;

		Payload(String message) {
			this.message = message;
			this.time = Instant.now();
		}
	}

	@Configuration
	class MessageHandler {

		private EmitterProcessor<Message<Payload>> processor = EmitterProcessor.create();

		@Bean
		public Supplier<Flux<Message<Payload>>> messageOutput() {
			return () -> processor;
		}

	}
}
