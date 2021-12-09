package com.smtek.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smtek.entity.Country;
import com.smtek.repository.CountryRepository;
import com.smtek.service.ICountryService;

@Service
public class CountryServiceImpl implements ICountryService {

	private CountryRepository countryReposiotry;
	
	@Autowired
	public CountryServiceImpl(CountryRepository repository) {
		this.countryReposiotry=repository;
	}
	
	@Override
	public Country addCountry(Country country) {
		return this.countryReposiotry.save(country);
	}

	@Override
	public List<Country> fetchAllCountries() {
		return this.countryReposiotry.findAll();
	}

	@Override
	public Optional<Country> fetchCountryById(int countryId) {
		return this.countryReposiotry.findById(countryId);
	}

	@Override
	public Optional<Country> fetchCountryByName(String countryName) {
		return this.countryReposiotry.findBycountryName(countryName);
	}

	@Override
	public Country updateCountry(Country country) {
		return this.countryReposiotry.save(country);
	}

	@Override
	public void deleteCountryById(int countryId) {
		this.countryReposiotry.deleteById(countryId);
	}

}
