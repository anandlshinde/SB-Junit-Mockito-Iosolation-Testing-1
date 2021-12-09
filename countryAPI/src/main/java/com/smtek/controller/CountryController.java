package com.smtek.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smtek.entity.Country;
import com.smtek.service.ICountryService;

@RestController
public class CountryController {
	
	private ICountryService iCountryService;
	
	@Autowired
	public CountryController(ICountryService countryService) {
		this.iCountryService=countryService;
	}
	
	
	@PostMapping("/country")
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		try {
			Country countryObj = this.iCountryService.addCountry(country);
			return new ResponseEntity<Country>(countryObj,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/countries")
	public ResponseEntity<?> fetchAllCountries(){
		try {
			List<Country> countries = this.iCountryService.fetchAllCountries();
			if(countries.isEmpty()) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Country>>(countries,HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/countryById/{countryId}")
	public ResponseEntity<Country> fetchCountryById(@PathVariable int countryId){
		try {
			Optional<Country> countryOptionalobj = this.iCountryService.fetchCountryById(countryId);
			if(!countryOptionalobj.isPresent()) {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<Country>(countryOptionalobj.get(),HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/countryByName/{countryName}")
	public ResponseEntity<Country> fetchCountryByName(@PathVariable String countryName){
		try {
			Optional<Country> countryOptionalObj = this.iCountryService.fetchCountryByName(countryName);
			if(!countryOptionalObj.isPresent()) {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Country>(countryOptionalObj.get(),HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/countryUpdate/{countryId}")
	public ResponseEntity<Country> updateCountry(@PathVariable int countryId,@RequestBody Country country){
		try {
			Optional<Country> countryOptionalObj = this.iCountryService.fetchCountryById(countryId);
			if(!countryOptionalObj.isPresent()) {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			Country countryObj=countryOptionalObj.get();
			countryObj.setCountryName(country.getCountryName());
			countryObj.setCapital(country.getCapital());
			Country updatedCountryObj = this.iCountryService.updateCountry(countryObj);
			return new ResponseEntity<Country>(updatedCountryObj,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/countrydeleteById/{countryId}")
	public ResponseEntity<?> deleteCountry(@PathVariable int countryId){
		try {
			Optional<Country> countryOptionalObj = this.iCountryService.fetchCountryById(countryId);
			if(!countryOptionalObj.isPresent()) {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			this.iCountryService.deleteCountryById(countryId);
			return new ResponseEntity<String>("Country deleted successfully with Id :: "+countryId,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}

}
