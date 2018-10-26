package io.chanchal.cloud.ribbonapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Configuration
public class RibbonAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonAppApplication.class, args);
	}

	@Autowired
	RestTemplate restTemplate;

	@Value("${serviceid}")
	String serviceid;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@RequestMapping("/")
	public String message()
	{
		ResponseEntity<String> response =	restTemplate.exchange("http://"+serviceid,HttpMethod.GET,null,String.class);

		return "Response from ribbon app "+response.getBody();
	}

}
