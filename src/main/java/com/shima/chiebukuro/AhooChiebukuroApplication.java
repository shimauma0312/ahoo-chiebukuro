package com.shima.chiebukuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shima.chiebukuro.model.SQLiteDBSetup;

@SpringBootApplication
public class AhooChiebukuroApplication {

	public static void main(String[] args) {
		SQLiteDBSetup.setup();
		SpringApplication.run(AhooChiebukuroApplication.class, args);
	}

}
