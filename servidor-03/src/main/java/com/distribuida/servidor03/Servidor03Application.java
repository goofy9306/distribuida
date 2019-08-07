package com.distribuida.servidor03;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableDiscoveryClient
 * 
 * 	permite inyectar la referencia a DiscoveryClient:
 * 
 *		@Autowired private DiscoveryClient discoveryClient;
 * 
 * con esto es posible buscar instancias registradas en el servidor EUREKA
 * 
 * @author jsalvador
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Servidor03Application implements CommandLineRunner {
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate( ) {
		return new RestTemplate();
	}
	
	@Autowired private DiscoveryClient discoveryClient;
	
	public static void main(String[] args) {
		SpringApplication.run(Servidor03Application.class, args);
	}

	@Override
    public void run(String... args) throws Exception {

		List<ServiceInstance> instancias = discoveryClient.getInstances( "S1" );
		
		System.out.println( "***************************" );
		
		instancias.forEach( s->{
			System.out.println( s.getUri( ) );
		});
		
		String url = instancias.get(0).getUri() + "/test";		
	}
	
	
	@Autowired RestTemplate rr;
	
	@GetMapping(path="/test")
	public String test( ) {
		return rr.getForObject( "http://s1/test", String.class );
	}
}








































