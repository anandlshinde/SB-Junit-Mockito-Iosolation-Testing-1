package com.smtek.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smtek.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Serializable> {

	public Optional<Country> findBycountryName(String countryName);
}
