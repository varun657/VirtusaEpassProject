package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Citizen;
import com.example.demo.model.StateWise;
import com.example.demo.services.CitizenService;
import com.example.demo.services.StatService;

@Controller
public class CitizenController {
	

	@Autowired
	private CitizenService citizenService;
	
	@Autowired
	private StatService statservice;
	  
    @GetMapping("/index")
    public String userIndex(Model model) {
    	 model.addAttribute("listCitizens", citizenService.getAllCitizens());
        return "index";
    } 
    
    @GetMapping("/checkPassApplication/{id}")
    public String  confirmOrDenyPass(@PathVariable(value = "id") long id, Model model)
    {
    	Citizen citizen=citizenService.getPassRequestById(id);
    	/*String source=citizen.getSource();
    	String destination=citizen.getDestination();
    	List<StateWise> stateWiseList=statservice.getStateWiseList();
    	String s1=new String();
		String s2=new String();
		int source_count=0,destination_count=0;
		for(int i=0;i<stateWiseList.size();i++)
		{
			StateWise s=stateWiseList.get(i);
			if(s.getStateName()==source)
			{
				s1=s.getActive();
				source_count=Integer.parseInt(s1);
				continue;
			}
			if(s.getStateName()==destination)
			{
				s2=s.getActive();
				destination_count=Integer.parseInt(s2);
				continue;
			}
		}
		if(source_count<=100000 && destination_count<=1000000)
		{
			citizen.setStatus("confirmed");
			citizenService.saveCitizen(citizen);
		}
		else
		{
			citizen.setStatus("denied");
			citizenService.saveCitizen(citizen);
		}
		*/
    	model.addAttribute("citizen", citizen);
		return "update_pass";
    }
    
    @PostMapping("/saveCiti")
    public String saveCiti(@ModelAttribute("citizen") Citizen citizen)
    {
    	citizenService.saveCitizen(citizen);
    	return "redirect:/index";
    }
}
