package com.akg;

import com.akg.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ActivationKeyGeneratorApplication {

	private static final Logger logger = LoggerFactory.getLogger(ActivationKeyGeneratorApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ActivationKeyGeneratorApplication.class, args);
		JwtService jwtService = context.getBean(JwtService.class);
		for (String arg : args) {
			logger.info("Arg: " + arg);
		}
	}

}
