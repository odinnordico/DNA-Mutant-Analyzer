package com.meli.test.dnamutantanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class DnaMutantAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnaMutantAnalyzerApplication.class, args);
	}

}
