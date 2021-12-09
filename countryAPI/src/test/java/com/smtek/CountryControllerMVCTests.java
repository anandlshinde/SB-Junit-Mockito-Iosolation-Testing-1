package com.smtek;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smtek.controller.CountryController;
import com.smtek.entity.Country;
import com.smtek.service.ICountryService;

/*@ComponentScan(basePackages = "com.smtek")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {CountryControllerMVCTests.class})*/
@WebMvcTest(value = CountryController.class)
@TestMethodOrder(OrderAnnotation.class)
public class CountryControllerMVCTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ICountryService iCountryService;
	
	@Autowired
	ObjectMapper mapper;

	/*
	 * @InjectMocks CountryController countryCntrl;
	 */

	public static List<Country> ListmockData() {
		List<Country> countries = new ArrayList<>();
		countries.add(new Country(1, "India", "Delhi"));
		countries.add(new Country(2, "USA", "Washington"));
		return countries;
	}

	public static Country countryObj() {
		return new Country(1, "India", "Delhi");
	}

	/*
	 * @BeforeEach public void setUp() {
	 * mockMvc=MockMvcBuilders.standaloneSetup(countryCntrl).build(); }
	 */

	@Test
	@Order(1)
	@DisplayName("Fetch All countries from database")
	public void test_fetchAllCountries_success() throws Exception {

		when(iCountryService.fetchAllCountries()).thenReturn(ListmockData());

		this.mockMvc.perform(get("/countries")).andExpect(status().isFound()).andDo(print());

	}

	@Test
	@Order(2)
	@DisplayName("Fetch All countries from database not found ")
	public void test_fetchAllCountries_notFound() throws Exception {

		List<Country> countries = Collections.emptyList();
		when(iCountryService.fetchAllCountries()).thenReturn(countries);

		this.mockMvc.perform(get("/countries")).andExpect(status().isNoContent()).andDo(print());

	}

	@Test
	@Order(3)
	@DisplayName("Fetch country by id success result")
	public void test_fetchCountryById_success() throws Exception {

		int countryId = 1;
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(countryObj()));

		this.mockMvc.perform(get("/countryById/{countryId}", countryId)).andExpect(status().isFound()).andDo(print());
	}

	@Test
	@Order(4)
	@DisplayName("Fetch country by id not found result")
	public void test_fetchCountryById_notFound() throws Exception {

		int countryId = 1;
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.empty());

		this.mockMvc.perform(get("/countryById/{countryId}", countryId)).andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	@Order(5)
	@DisplayName("Fetch country by name success result")
	public void test_fetchCountryByName_success() throws Exception {
		String countryName = "India";

		when(iCountryService.fetchCountryByName(countryName)).thenReturn(java.util.Optional.of(countryObj()));

		this.mockMvc.perform(get("/countryByName/{countryName}", countryName)).andExpect(status().isFound())
				.andDo(print());

	}

	@Test
	@Order(6)
	@DisplayName("Fetch country by name no content result")
	public void test_fetchCountryByName_notFound() throws Exception {
		String countryName = "India";

		when(iCountryService.fetchCountryByName(countryName)).thenReturn(java.util.Optional.empty());

		this.mockMvc.perform(get("/countryByName/{countryName}", countryName))
					.andExpect(status().isNotFound())
					.andDo(print());

	}
	
	@Test
	@Order(7)
	@DisplayName("Add country data in database success result")
	public void test_addCountry_success() throws Exception {
		
		String countryJsonObj = mapper.writeValueAsString(countryObj());
		
		when(iCountryService.addCountry(countryObj())).thenReturn(countryObj());
		
		this.mockMvc.perform(post("/country").content(countryJsonObj).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andExpect(MockMvcResultMatchers.jsonPath(".countryId").value(1))
					.andDo(print());
	}
	
	@Test
	@Order(8)
	@DisplayName("Update country data in database success result")
	public void test_updateCountry_success() throws Exception {
		String countryJsonObj=mapper.writeValueAsString(countryObj());
		int countryId=1;
		
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(countryObj()));
		when(iCountryService.updateCountry(countryObj())).thenReturn(countryObj());
		
		this.mockMvc.perform(put("/countryUpdate/{countryId}",countryId).content(countryJsonObj).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
					.andDo(print());
		
	}
	
	@Test
	@Order(9)
	@DisplayName("Delete country by id from database success result")
	public void test_deleteCountry_success() throws Exception {
		int countryId=1;
		
		when(iCountryService.fetchCountryById(countryId)).thenReturn(java.util.Optional.of(countryObj()));
		
		this.mockMvc.perform(delete("/countrydeleteById/{countryId}",countryId))
					.andExpect(status().isOk());
	}

}
