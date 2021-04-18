package com.makeathon.sehad;

//import com.makeathon.sehad.zuul.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@PropertySource("classpath:application.properties")
public class SehadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SehadApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public ZuulAuthenticationFilter getAuthenticatedFilter () {
//		return new ZuulAuthenticationFilter();
//	}
}
