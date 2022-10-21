package com.example.demo;

import com.example.demo.student.Student;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@GetMapping
//	public String hello() {
//		return "Hello World";
//	}


}
