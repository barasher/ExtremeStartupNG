package com.barasher.esng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] { Main.class, Context.class }, args);
	}

}