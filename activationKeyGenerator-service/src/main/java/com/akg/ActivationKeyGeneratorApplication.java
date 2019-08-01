package com.akg;

import com.akg.lang.SigningKeyInvalidException;
import com.akg.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ActivationKeyGeneratorApplication {

	private static final Logger logger = LoggerFactory.getLogger(ActivationKeyGeneratorApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ActivationKeyGeneratorApplication.class, args);
		JwtService jwtService = context.getBean(JwtService.class);
		    if (args.length == 0) {
				logger.info("No commands entered.");
			}
			switch (args[0]) {
				case "createtoken":
					logger.info("Create token...");
					if (args.length == 6) {
						jwtService.createJWT(args[1], args[2], args[3], args[4], Long.parseLong(args[5]));
					} else {
						logger.error("\nTo create the token, 5 parameters must be entered:\n" +
								"id\n" +
								"issuer\n" +
								"subject\n" +
								"secretKey\n" +
								"ttlMillis (Expiration Time in Milliseconds)");
					}
					break;
				case "createfile":
					logger.info("Create file...");
					if (args.length == 3) {
						try {
							jwtService.createFile(args[1], args[2]);
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					} else {
						logger.error("\nTo create the file, 2 parameters are required:\n" +
								"token: The token to insert into the file.\n" +
								"fileDestination: The destination of the file. Example: /home/user/active.key");
					}
					break;
				case "readKeyfile":
					logger.info("File Reading...");
					if (args.length == 2) {
						jwtService.readActivationKeyFile(args[1]);
					} else {
						logger.error("\nTo read the file, 1 parameter is required:\n" +
								"fileDestination: The location of the file.");
					}
					break;
				case "decode":
					logger.info("Decoding token...");
					if (args.length == 3) {
						try {
							jwtService.decodeJWT(args[1], args[2]);
						} catch (SigningKeyInvalidException e) {
							logger.error(e.getMessage());
						}
					} else {
						logger.error("\nTo decode the token, 2 parameters are required:\n" +
								"token: The token to be decoded;\n" +
								"secretykey: The secret key used to encode the token.");
					}
					break;
				case "tokenexpiration":
					logger.info("Token Expiration Check...");
					if (args.length == 3) {
						jwtService.checkForWorldTimeAPITokenExpiration(args[1], args[2]);
					} else {
						logger.error("\nTo decode the token, 2 parameters are required:\n" +
								"token: The token to be decoded;\n" +
								"secretykey: The secret key used to encode the token.");
					}
					break;
				case "help":
					logger.info("\nRunning the serviro to reach activation key:\n" +
							"- createtoken id issuer subject secretykey timeExpiration\n" +
							"      - id: The token identifier;\n" +
							"      - issuer: The issuer of the token;\n" +
							"      - subject: For whom the token will be created (Suggestion);\n" +
							"      - secretyKey: Secret key to decode this token;\n" +
							"      - timeExpiration: Time, in milliseconds, for token validation.\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar createtoken 123 test me KJASHDKJSAD 2000\n" +
							"\n" +
							"\n" +
							"- createfile token filedestination\n" +
							"      - token: The token that will be inserted into the file;\n" +
							"      - fileDestination: Path where the file will be created.\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar KJASGDKHS /home/user/active.key\n" +
							"\n" +
							"\n" +
							"- readKeyfile fileDestination\n" +
							"      - fileDestination: The path to read file only. It will present the content that is inside the file;\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar readKeyfile /home/user/active.key\n" +
							"\n" +
							"\n" +
							"- decode token secretkey\n" +
							"      - token: The token that will be decoded;\n" +
							"      - secretkey: The secret key used to encode the token.\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar decode KJASHDKJASH TTER5612\n" +
							"\n" +
							"\n" +
							"- tokenexpiration token secretkey\n" +
							"      - token: The token that will be decoded;\n" +
							"      - secretkey: The secret key used to encode the token.\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar decode KJASHDKJASH TTER5612\n" +
							"\n" +
							"\n" +
							"- help\n" +
							"    Displays some basic command.\n" +
							"Example:\n" +
							"java -jar activationKeyGenerator-service.jar help");
					break;
				default:
					logger.error("No commands found.");
			}
	}

}
