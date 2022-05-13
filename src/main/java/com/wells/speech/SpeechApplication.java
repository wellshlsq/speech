package com.wells.speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;




import java.util.Arrays;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

@RestController
//@CrossOrigin(origins = "https://localhost:4200")
@CrossOrigin(origins = "http://localhost:8080")
public class SpeechApplication {

	@Autowired
	ConfigProperties configProp;
	static String SUBSCRPTION_KEY = "speech.subscription-key";
	static String OUTPUT_FORMAT = "speech.outputFormat";
	static String AZURE_SPEECH_SERVICE_ENDPOINT = "speech.azureSpeechEndPoint";
	@GetMapping("/message")
	public ResponseEntity message() throws Exception{

		//String uri = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";

		RestTemplate template = new RestTemplate();
		String payload = "<speak version='1.0' xml:lang='en-US'><voice xml:lang='en-US' xml:gender='Male'\n" +
				"name='en-US-ChristopherNeural'>\n" +
				"Bodhi Bjorn Joaquin\n" +
				"</voice></speak>";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.valueOf("*/*")));

		headers.setContentType(MediaType.valueOf("application/ssml+xml"));
		headers.set("Ocp-Apim-Subscription-Key", configProp.getConfigValue(SUBSCRPTION_KEY));
		headers.set("X-Microsoft-OutputFormat", configProp.getConfigValue(OUTPUT_FORMAT));
		headers.setConnection("keep-alive");

		HttpEntity<Object> requestEntity =
				new HttpEntity<>(payload, headers);

		ResponseEntity<byte[]> entity = template.exchange(configProp.getConfigValue(AZURE_SPEECH_SERVICE_ENDPOINT), HttpMethod.POST, requestEntity,
				byte[].class);

		return entity;
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
