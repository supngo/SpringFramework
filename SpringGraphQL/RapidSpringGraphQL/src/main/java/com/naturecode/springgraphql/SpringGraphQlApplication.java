package com.naturecode.springgraphql;

import com.naturecode.springgraphql.models.Car;
import com.naturecode.springgraphql.services.CarService;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringGraphQlApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringGraphQlApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CarService carService) {
		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
						"AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").forEach(name -> {
				Car car = new Car();
				car.setName(name);
				carService.saveCar(car);
			});
			carService.getCars().forEach(System.out::println);
		};
	}
}
