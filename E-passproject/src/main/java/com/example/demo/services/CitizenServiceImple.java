package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Citizen;
import com.example.demo.repository.CitizenRepository;

@Service
public class CitizenServiceImple implements CitizenService {
	 
	@Autowired
	private CitizenRepository citizenRepository;
	
	@Override
	 public List <Citizen> getAllCitizens() {
       return citizenRepository.findAll();
   }

   @Override
   public void saveCitizen(Citizen citizen) {
       this.citizenRepository.save(citizen);
   }
   
   @Override
   public Citizen getPassRequestById(long id) {
       Optional < Citizen > optional = citizenRepository.findById(id);
       Citizen passrequest = null;
       if (optional.isPresent()) {
           passrequest = optional.get();
       } else {
           throw new RuntimeException(" Pass Request not found for id :: " + id);
       }
       return passrequest;
   }
}
