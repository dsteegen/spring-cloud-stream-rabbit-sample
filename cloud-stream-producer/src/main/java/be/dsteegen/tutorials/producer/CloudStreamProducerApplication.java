package be.dsteegen.tutorials.producer;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@SpringBootApplication
@EnableBinding(Source.class)
public class CloudStreamProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamProducerApplication.class, args);
	}

	@RestController
    @RequiredArgsConstructor
	@Transactional
    static class ProducerController {

	    private final Source source;

		@PostMapping("/post/{message}")
		void postOnQueue(@PathVariable String message) {
            var payload = withPayload(new Payload(message)).build();
            source.output().send(payload);
		}

	}

	@Value
	static class Payload {
		String message;
		Instant time;

		Payload(String message) {
			this.message = message;
			this.time = Instant.now();
		}
	}
}
