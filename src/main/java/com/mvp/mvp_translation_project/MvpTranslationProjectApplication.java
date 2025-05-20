package com.mvp.mvp_translation_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MvpTranslationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvpTranslationProjectApplication.class, args);
	}


}
