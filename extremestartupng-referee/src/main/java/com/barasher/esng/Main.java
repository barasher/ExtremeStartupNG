package com.barasher.esng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
	final ConfigurableApplicationContext ctx = SpringApplication.run(new Object[] { Main.class, Context.class },
		args);
    }

}