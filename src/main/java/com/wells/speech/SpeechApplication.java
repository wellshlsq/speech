package com.wells.speech;

import com.wells.speech.models.Recording;
import com.wells.speech.models.RecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
@RestController
@CrossOrigin(origins = "*")
public class SpeechApplication {

	@Autowired
	ConfigProperties configProp;
	@Autowired
	RecordingRepository recordingRepository;

	static String SUBSCRPTION_KEY = "speech.subscription-key";
	static String OUTPUT_FORMAT = "speech.outputFormat";
	static String AZURE_SPEECH_SERVICE_ENDPOINT = "speech.azureSpeechEndPoint";

	@GetMapping("/message")
	public ResponseEntity message() throws Exception{

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
		Recording newRecord = new Recording();
		newRecord.setName(audioRecording.getName());
		newRecord.setAudioblob(audioRecording.getAudioBlob().getBytes());
		recordingRepository.save(newRecord);
		return "Congrats !!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpeechApplication.class, args);
	}

}
