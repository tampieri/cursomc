package com.tampieri.lojavirtual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaVitualApplication implements CommandLineRunner {
	
	//@Autowired
	//private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(LojaVitualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {	
		//s3Service.uploadFile("D:\\temp\\fotos\\Samara6.jpg");
	}	
}
