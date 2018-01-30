package com.example.soap.springboot.soapSpringboot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private CountryRepository countryRepository;
	@Autowired
	private Tracer tracer;
	
	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		try {
			response.setCountry(countryRepository.findCountry(request.getName()));
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			Span span = tracer.getCurrentSpan();
			//tracer.detach(span);
			headers.add("traceId", span.traceIdString());
			headers.add("spanId", Span.idToHex(span.getSpanId()));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> requestRest = new HttpEntity<>("{}", headers);
			//Comentado para outros testes
			//restTemplate.exchange("http://127.0.0.1:9091/value/test", HttpMethod.POST, requestRest, String.class);
			tracer.close(span);
		} catch (Exception e) {
			System.out.println("Error"+ e.getMessage());
		}
		return response;
	}	

}