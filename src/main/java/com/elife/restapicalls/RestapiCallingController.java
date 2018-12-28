package com.elife.restapicalls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "RestApiCalling")
public class RestapiCallingController {

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	
	@Value("${serviceurl.api}")
	String serviceUrl;

	@Value("${foundryServerUrl}")
	String foundryServerUrl;

	@ApiOperation(value = "getMethodCalling")
	@RequestMapping(value = "/api/get", method = RequestMethod.GET)
	public String getMethodCalling() {
		return restTemplate.getForObject(serviceUrl, String.class);
	}

	@ApiOperation(value = "foundryAPICalling")
	@RequestMapping(value = "/api/foundryapicalling", method = RequestMethod.GET)
	public String getDigitalSignageData() {
		// ResponseEntity<PromotionBannerData> response = null;
		String response = null;
		String digitalSignageUrl = foundryServerUrl	+ "/posttest";
		response = restTemplate.getForObject(digitalSignageUrl, String.class);
		return response;
	}

	
	@ApiOperation(value = "salesforceAPICalling")
	@RequestMapping(value = "/api/sfapicalling", method = RequestMethod.GET, produces = "application/json")
	public String salesForceCustomerData() {
		String response = null;
		// Step 1 : Generate token and other stuff we need to hit the Ouath URL.
		// Step 2 : Once hit the response will genearate as json with Access_token and other stuff
		// Step 3 : Use String operation and get the token value and pass to POST method to get the response.
		String sfServerUrl = "";
		
		response = restTemplate.postForObject(sfServerUrl, null, String.class);
		String[] list=response.split(":");
		String token=list[1];
		String tokens[]=token.split(",");
		String finalToken = tokens[0].replaceAll("\"", "").trim();

		String sfCustomerUrl = "https://localhost:8089/api/swagger/posttest";

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(sfCustomerUrl);
		
		ClientResponse resp = webResource.accept("application/json").header("authorization", "Bearer " + finalToken)
				.get(ClientResponse.class);
		if (resp.getStatus() != 200) {
			System.err.println("Unable to connect to the server");
		}
		String output = resp.getEntity(String.class);
		System.out.println("response: " + output);
		
		return output;
	}

}
