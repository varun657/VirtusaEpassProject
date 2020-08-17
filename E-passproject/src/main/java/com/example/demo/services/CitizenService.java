package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Citizen;
public interface CitizenService {
	
	List<Citizen> getAllCitizens();
	void saveCitizen(Citizen citizen);

	Citizen getPassRequestById(long id);
}
