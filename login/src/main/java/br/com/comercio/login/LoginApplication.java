package br.com.comercio.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableEurekaClient
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

}
