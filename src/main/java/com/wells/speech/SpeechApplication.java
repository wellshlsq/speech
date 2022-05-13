package com.wells.speech;

import com.wells.speech.dbcon.BasicConnectionPool;
import com.wells.speech.dbcon.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.util.Arrays;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
@CrossOrigin(origins = "https://localhost:4200")

public class SpeechApplication {

	@Autowired
	ConfigProperties configProp;

	@Autowired
	BasicConnectionPool connPool;
	static String SUBSCRPTION_KEY = "speech.subscription-key";
	static String OUTPUT_FORMAT = "speech.outputFormat";
	static String AZURE_SPEECH_SERVICE_ENDPOINT = "speech.azureSpeechEndPoint";

	@GetMapping("/message")
	public ResponseEntity message() throws Exception{
		try {
			System.out.println("Getting connection from pool..");
			Connection conn = connPool.getConnection();
			if(conn != null)
				System.out.println("Got Connection from the pool");

		} catch(Exception se) {
			System.out.println("SQLException : " + se.getMessage());
			se.printStackTrace();
		}
		String uri = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";
		System.out.println("Sounding name out..");

		RestTemplate template = new RestTemplate();
		//CreateObjectInput payload = new CreateObjectInput();
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
		//byte[] body = entity.getBody();
		//ResponseBody respBody
		//InputStream in = servletContext.getResourceAsStream(entity);

		return entity;
	}

	@RequestMapping(value="/register",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public String register() {
		//Response resp = "Congrats !!";
		return "Congrats !!";
	}

	@PostMapping(value="/uploadRecording")
	public String uploadRecording(@RequestBody AudioRecording audioRecording) {
		System.out.println(audioRecording.getName());
		System.out.println(audioRecording.getAudioBlob());
		byte[] byteArrray = audioRecording.getAudioBlob().getBytes();
		return "Congrats !!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpeechApplication.class, args);
	}

}
