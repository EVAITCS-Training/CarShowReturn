package com.horrorcore.car_show;

import org.springframework.boot.SpringApplication;

public class TestCarShowApplication {

	public static void main(String[] args) {
		SpringApplication.from(CarShowApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
