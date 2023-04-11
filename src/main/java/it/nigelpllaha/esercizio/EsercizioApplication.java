package it.nigelpllaha.esercizio;

import it.nigelpllaha.esercizio.config.EsercizioConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties (EsercizioConfigProperties.class)
public class EsercizioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsercizioApplication.class, args);
	}

}
