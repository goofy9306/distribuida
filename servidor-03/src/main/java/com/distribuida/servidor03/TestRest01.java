package com.distribuida.servidor03;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
@RestController
public class TestRest01 {

	@Autowired private DiscoveryClient discoveryClient;
	
	@Autowired private RestTemplate restTemplate;
	
	
	private void listarServidores( String key ) {

		List<ServiceInstance> instancias = discoveryClient.getInstances( key );
		
		System.out.println( );
		System.out.println( "----------------------------------------" );
		System.out.printf( "Servidores %s registrados: %d\n", key, instancias.size() );
		System.out.println( "----------------------------------------" );

		int index = 1;
		for( ServiceInstance ins: instancias ) {
			
			//System.out.println( "host: " + ins.getHost( ) );
			//System.out.println( "port: " + ins.getPort( ) );
			System.out.printf( "%d: uri : %s\n", index++, ins.getUri( ) );
		}
	}
	
	@PostConstruct
	public void init( ) {
		
		listarServidores( "S1" );
		
//		System.out.println( "invocación: " + mensaje1 );
//		System.out.println( "invocación: " + mensaje2 );
	}
	
	@GetMapping(path="/kk")
	public String kk( ) {
		return restTemplate.getForObject( "http://S1/test", String.class );
	}
}
