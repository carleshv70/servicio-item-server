package com.chuix.formacio.springboot.appzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class SpringbootServicioZuulServer2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioZuulServer2Application.class, args);
	}

}
