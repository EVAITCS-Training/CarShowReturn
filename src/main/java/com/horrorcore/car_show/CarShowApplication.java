package com.horrorcore.car_show;

import com.horrorcore.car_show.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarShowApplication {

	@Autowired
	private CarService carService;

	public static void main(String[] args) {
		SpringApplication.run(CarShowApplication.class, args);
	}

}
