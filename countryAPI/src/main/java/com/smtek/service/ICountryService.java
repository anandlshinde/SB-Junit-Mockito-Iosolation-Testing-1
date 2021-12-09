package com.smtek.service;

import java.util.List;
import java.util.Optional;

import com.smtek.entity.Country;

public interface ICountryService {

	public Country addCountry(Country country);
	
	public List<Country> fetchAllCountries();
	
	public Optional<Country> fetchCountryById(int countryId);
	
	public Optional<Country> fetchCountryByName(String countryName);
	
	public Country updateCountry(Country country);
	
	public void deleteCountryById(int countryId);
}
