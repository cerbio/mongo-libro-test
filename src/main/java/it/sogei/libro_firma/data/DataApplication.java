package it.sogei.libro_firma.data;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableRetry
@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "DataApplication",
                version = "1.0",
                description = "Microservizio per la gestione dei dati dei documenti"
        )
)
public class DataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}

	/**
	 * Bean di utilità per la conversione di Entity a DTO e viceversa
	 * */
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
}
