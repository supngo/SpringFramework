package com.naturecode.test;

import java.util.stream.Stream;

import com.naturecode.test.models.Greeting;
import com.naturecode.test.services.GreetingService;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer  
@EnableOAuth2Sso
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	ApplicationRunner init(GreetingService greetingService) {
		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
						"AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").forEach(message -> {
				Greeting greeting = new Greeting();
				greeting.setMessage(message);
				greetingService.saveGreeting(greeting);
			});
			greetingService.getGreetings().forEach(System.out::println);
		};
	}
}
