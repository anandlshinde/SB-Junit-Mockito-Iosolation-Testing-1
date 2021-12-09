package com.smtek;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smtek.entity.Country;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Disabled
public class CountryApiIntegrationTests {
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@Test
	@Order(3)
	@DisplayName("Fetch countries from Database success result")
	public void test_fetchAllCountries_success() throws JSONException {


		String expectedResult="[{\r\n" + 
				"    \"countryId\": 1,\r\n" + 
				"    \"countryName\": \"India\",\r\n" + 
				"    \"capital\": \"Delhi1\"\r\n" + 
				"}]";

		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:9090/countryapp/countries",
				String.class);

		System.out.println("DATA : " + responseEntity.getBody());
		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);

	}

	@Test
	@Order(4)
	@DisplayName("Fetch country by id success result")
	public void test_fetchCountryById_success() throws JSONException {

		String expectedResult = "{\r\n" + 
				"        \"countryId\": 1,\r\n" + 
				"        \"countryName\": \"India\",\r\n" + 
				"        \"capital\": \"Delhi1\"\r\n" + 
				"    }";

		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:9090/countryapp/countryById/1", String.class);

		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
	}

	@Test
	@Order(5)
	@DisplayName("Fetch country by name success result")
	public void test_fetchCountryByName_success() throws JSONException {

		String expectedResult = "{\r\n" + 
				"        \"countryId\": 1,\r\n" + 
				"        \"countryName\": \"India\",\r\n" + 
				"        \"capital\": \"Delhi1\"\r\n" + 
				"    }";
		
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:9090/countryapp/countryByName/India", String.class);

		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
	}
	
	@Test
	@Order(1)
	@DisplayName("Add country in database success result")
	public void test_addCountry_success() throws JSONException, JsonProcessingException {
		
		String expectedResult="{\r\n" + 
				"    \"countryId\": 1,\r\n" + 
				"    \"countryName\": \"India\",\r\n" + 
				"    \"capital\": \"Delhi\"\r\n" + 
				"}";
		Country country=new Country(1, "India", "Delhi");
		
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request=new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:9090/countryapp/country", request, String.class);
		
		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
	}
	
	@Test
	@Order(2)
	@DisplayName("Update country data in database success result")
	public void test_updateCountry_success() throws JSONException {
		
		String expectedResult="{\r\n" + 
				"    \"countryId\": 1,\r\n" + 
				"    \"countryName\": \"India\",\r\n" + 
				"    \"capital\": \"Delhi1\"\r\n" + 
				"}";
		Country country=new Country(1, "India", "Delhi1");
		
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request=new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:9090/countryapp/countryUpdate/1",HttpMethod.PUT,request,String.class);
				
		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
	}
	
	@Test
	@Order(6)
	@DisplayName("Delete country from database success result")
	public void test_deleteCountry() throws JSONException {
		
		Country country=null;
		String expectedResult="Country deleted successfully with Id :: "+1;
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request=new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:9090/countryapp/countrydeleteById/1",HttpMethod.DELETE,request, String.class);
		
		//JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
		assertEquals(expectedResult, responseEntity.getBody().toString());
	}
}
