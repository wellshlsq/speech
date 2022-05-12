package com.wells.speech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
@CrossOrigin(origins = "https://localhost:4200")

public class SpeechApplication {

	@GetMapping("/message")
	public ResponseEntity message(){
		//return "Congrats ! your application deployed successfully in Azure Platform. !";


		String uri = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";

		//RestTemplate restTemplate = new RestTemplate();
		//Object result = restTemplate.pos(uri, Object.class);
		System.out.println("test");

		RestTemplate template = new RestTemplate();
		//CreateObjectInput payload = new CreateObjectInput();
		String payload = "<speak version='1.0' xml:lang='en-US'><voice xml:lang='en-US' xml:gender='Male'\n" +
				"name='en-US-ChristopherNeural'>\n" +
				"Bodhi Bjorn Joaquin\n" +
				"</voice></speak>";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.valueOf("*/*")));

		headers.setContentType(MediaType.valueOf("application/ssml+xml"));
		headers.set("Ocp-Apim-Subscription-Key", "4c58729192b0462ba6f666625ab8e9c8");
		headers.set("X-Microsoft-OutputFormat", "audio-24khz-160kbitrate-mono-mp3");
		headers.setConnection("keep-alive");

		/*List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);

		template.setMessageConverters(messageConverters);*/

		HttpEntity<Object> requestEntity =
				new HttpEntity<>(payload, headers);

		/*Object response =
				template.exchange(uri, HttpMethod.POST, requestEntity,
						byte[].class);*/


		ResponseEntity<byte[]> entity = template.exchange(uri, HttpMethod.POST, requestEntity,
				byte[].class);
		//byte[] body = entity.getBody();
		//ResponseBody respBody
		//InputStream in = servletContext.getResourceAsStream(entity);

		return entity;
		//return "Congrats ! your application deployed successfully in Azure Platform. !";
	}

	@RequestMapping(value="/register",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public String register() {
		//Response resp = "Congrats !!";
		return "Congrats !!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpeechApplication.class, args);
	}

}
