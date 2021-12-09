package com.smtek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.smtek.entity.Country;
import com.smtek.repository.CountryRepository;
import com.smtek.serviceImpl.CountryServiceImpl;

@SpringBootTest(classes = {CountryServiceTests.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryServiceTests {
 
	@Mock
	CountryRepository countryRepository;
	
	@InjectMocks
	CountryServiceImpl countryServiceImpl;
	
	public static List<Country> mockData(){
		List<Country> countries=new ArrayList<>();
		countries.add(new Country(1, "India","Delhi"));
		countries.add(new Country(2,"USA","Washington"));
		return countries;
	}
	
	
	@Test
	@Order(1)
	@DisplayName("Fetch all countries success")
	public void test_fetchAllCountries_success() {
		List<Country> countries=mockData();
		
		when(countryRepository.findAll()).thenReturn(countries); //MockObjct
		
		List<Country> fetchAllCountries = countryServiceImpl.fetchAllCountries();
			
		assertEquals(fetchAllCountries.size(), countries.size());
		assertEquals(fetchAllCountries.get(1).getCountryName(), countries.get(1).getCountryName());
	}
	
	@Test
	@Order(2)
	@DisplayName("Fetch all countries no data")
	public void test_fetchAllCountries_noData() {
		List<Country> fetchAllCountries = countryServiceImpl.fetchAllCountries();
		
		assertEquals(fetchAllCountries.size(), 0);
	}
	
	@Test
	@Order(3)
	@DisplayName("Fetch country by Name success result")
	public void test_fetchCountryByName_success() {
		Country country=new Country(1, "India","Delhi");
		String countryName="India";
		
		when(countryRepository.findBycountryName(countryName)).thenReturn(java.util.Optional.of(country));
		
		Optional<Country> actualResult = countryServiceImpl.fetchCountryByName(countryName);
		
		assertEquals(country.getCountryName(), actualResult.get().getCountryName());
	}
	
	@Test
	@Order(4)
	@DisplayName("Fetch country by Name empty result")
	public void test_fetchCountryByName_not_Found() {
		Optional<Country> country=java.util.Optional.empty();
		String countryName="India";
		
		when(countryRepository.findBycountryName(countryName)).thenReturn(country);
		
		Optional<Country> actualResult = countryServiceImpl.fetchCountryByName(countryName);
		
		assertEquals(country, actualResult);
	}
	
	@Test
	@Order(5)
	@DisplayName("Fetch country by Id success result")
	public void test_fetchCountryById_success() {
		Country country=new Country(1, "India","Delhi");
		int countryId=1;
		
		when(countryRepository.findById(countryId)).thenReturn(java.util.Optional.of(country));
		
		Optional<Country> actualResult = countryServiceImpl.fetchCountryById(countryId);
		
		assertEquals(country.getCountryName(), actualResult.get().getCountryName());
	}
	
	@Test
	@Order(6)
	@DisplayName("Fetch country by Id empty result")
	public void test_fetchCountryById_not_Found() {
		Optional<Country> country=java.util.Optional.empty();
		int countryId=1;
		
		when(countryRepository.findById(countryId)).thenReturn(country);
		
		Optional<Country> actualResult = countryServiceImpl.fetchCountryById(countryId);
		
		assertEquals(country, actualResult);
	}
	
	@Test
	@Order(7)
	@DisplayName("Add country in database success result")
	public void test_addCountry_success() {
		Country country=new Country(1,"India","Delhi");
		
		when(countryRepository.save(country)).thenReturn(country);
		
		Country actualResult = countryServiceImpl.addCountry(country);
		
		assertEquals(country.getCountryName(), actualResult.getCountryName());
	}
	
	@Test
	@Order(8)
	@DisplayName("Update country in database success result")
	public void test_updateCountry_success() {
		Country country=new Country(1,"India","Delhi");
		
		when(countryRepository.save(country)).thenReturn(country);
		
		Country actualResult = countryServiceImpl.addCountry(country);
		
		assertEquals(country.getCountryName(), actualResult.getCountryName());
	}
	
	@Test
	@Order(9)
	@DisplayName("Delete country by Id success result")
	public void test_deleteCountry_success() {
		int countryId=1;
		countryServiceImpl.deleteCountryById(countryId);
		verify(countryRepository,times(1)).deleteById(countryId);
	}
	
	
	
}
