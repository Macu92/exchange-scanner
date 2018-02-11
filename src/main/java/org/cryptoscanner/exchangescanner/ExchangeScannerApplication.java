package org.cryptoscanner.exchangescanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeScannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeScannerApplication.class, args);
	}
}
