package com.smtek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smtek.controller.CountryController;
import com.smtek.entity.Country;
import com.smtek.service.ICountryService;

@SpringBootTest(classes = {CountryControllerTests.class})
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CountryControllerTests {

	@Mock
	private ICountryService iCountryService;
	
	@InjectMocks
	private CountryController countryCntrl;
	
	public static List<Country> ListmockData(){
		List<Country> countries=new ArrayList<>();
		countries.add(new Country(1, "India","Delhi"));
		countries.add(new Country(2,"USA","Washington"));
		return countries;
	}
	
	public static Country countryObj() {
		return new Country(1, "India","Delhi");
	}
	
	
	@Test
	@Order(1)
	@DisplayName("Fetch All countries success")
	public void test_fetchAllCountries_sucess() {
		List<Country> countries=ListmockData();
		
		when(iCountryService.fetchAllCountries()).thenReturn(countries);
		
		 ResponseEntity<?> responseResult = countryCntrl.fetchAllCountries();
		 
		 assertEquals(HttpStatus.FOUND.value(), responseResult.getStatusCode().value());
		 
	}
	
	
	@Test
	@Order(2)
	@DisplayName("Fetch All countries not content")
	public void test_fetchAllCountries_no_Content() {
		List<Country> countries=Collections.emptyList();
		
		when(iCountryService.fetchAllCountries()).thenReturn(countries);
		
		 ResponseEntity<?> responseResult = countryCntrl.fetchAllCountries();
		 
		 assertEquals(HttpStatus.NO_CONTENT.value(), responseResult.getStatusCode().value());
		 
	}

	@Test
	@Order(3)
	@DisplayName("Fetch country by Id success result")
	public void test_fetchCountryById_success() {
		Country country=countryObj();
		int countryId=1;
		
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(country));
		
		ResponseEntity<?> actualResult = countryCntrl.fetchCountryById(countryId);
		
		assertEquals(HttpStatus.FOUND, actualResult.getStatusCode());
		assertEquals(country, actualResult.getBody());
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Fetch country by Id not found")
	public void test_fetchCountryById_notFound() {
		Optional<Country> country=java.util.Optional.empty();
		int countryId=1;
		
		when(iCountryService.fetchCountryById(countryId)).thenReturn(country);
		
		ResponseEntity<?> actualResult = countryCntrl.fetchCountryById(countryId);
		
		assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
		
	}
	
	@Test
	@Order(5)
	@DisplayName("Fetch country by Name success result")
	public void test_fetchCountryByName_success() {
		Country country=countryObj();
		String countryName="India";
		
		when(iCountryService.fetchCountryByName(countryName)).thenReturn(java.util.Optional.of(country));
		
		ResponseEntity<Country> actualResult = countryCntrl.fetchCountryByName(countryName);
		
		assertEquals(HttpStatus.FOUND, actualResult.getStatusCode());
		assertEquals(countryName,actualResult.getBody().getCountryName());
	}
	
	
	@Test
	@Order(6)
	@DisplayName("Fetch country by Name not found result")
	public void test_fetchCountryByName_notFound() {
		Optional<Country> country=java.util.Optional.empty();
		String countryName="India";
		
		when(iCountryService.fetchCountryByName(countryName)).thenReturn(country);
		
		ResponseEntity<Country> actualResult = countryCntrl.fetchCountryByName(countryName);
		
		assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
	}
	

	@Test
	@Order(7)
	@DisplayName("Add country success result")
	public void test_addCountry_success() {
		Country country=countryObj();
		
		when(iCountryService.addCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> actualResult = countryCntrl.addCountry(country);
		
		assertEquals(HttpStatus.CREATED, actualResult.getStatusCode());
		assertEquals(country, actualResult.getBody());
	}
	
	@Test
	@Order(8)
	@DisplayName("Update country success result")
	public void test_updateCountry_success() {
		Country country=countryObj();
		int countryId=1;
		
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(country));
		
		when(iCountryService.updateCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> actualResult = countryCntrl.updateCountry(countryId, country);
		
		assertEquals(HttpStatus.OK, actualResult.getStatusCode());
		assertEquals(country.getCountryName(), actualResult.getBody().getCountryName());
		
	}
	
	@Test
	@Order(9)
	@DisplayName("Delete country from database success result")
	public void test_deleteCountry_success() {
		Country country=countryObj();
		int countryId=1;
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(country));
		ResponseEntity<?> actualResult = countryCntrl.deleteCountry(countryId);
		assertEquals(HttpStatus.OK, actualResult.getStatusCode());
	}
	
	@Test
	@Order(10)
	@DisplayName("Delete country from database not found result")
	public void test_deleteCountry_notFound() {
		int countryId=1;
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.empty());
		ResponseEntity<?> actualResult = countryCntrl.deleteCountry(countryId);
		assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
	}
}
