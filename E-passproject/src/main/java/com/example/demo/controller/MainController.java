package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Citizen;
import com.example.demo.model.StateWise;
import com.example.demo.services.CitizenService;
import com.example.demo.services.StatService;

@Controller
public class MainController {
	
	@Autowired
	private CitizenService citizenService;
	
	@Autowired
	private StatService statService;
	
	@GetMapping("/")
	public String welcome(Model model) {
		return "welcome";
	}
	
	@GetMapping("/welcome")	
	public String goBackToMainPage(Model model) {
		
		return "welcome";
	}

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @GetMapping("/home")
    public String userHome(Model model)
    {
    	
    	return "home";
    }
    
    @GetMapping("/register_citizen")
    public String showRegistration(Model model)
    {
    	return "register_citizen";
    }
    
    @GetMapping("/showNewCitizenForm")
    public String showNewCitizenForm(Model model) {
        // create model attribute to bind form data
        Citizen citizen = new Citizen();
        model.addAttribute("citizen", citizen);
        return "home/register_citizen";
    }
    
    @PostMapping("/saveCitizen")
    public String saveCitizen(@ModelAttribute("citizen") Citizen citizen, Model model) {
        // save citizen to database
        citizenService.saveCitizen(citizen);
        model.addAttribute("success", "Your pass has been succesfully applied! ");
        return "home/register_citizen";
    }
    
    @GetMapping("/checkPass")
	public String checkForPassWithId(Model model)
	{
		Citizen pass=new Citizen();
		model.addAttribute("pass", pass);
		return "checkPassStat";
	}
	
	@GetMapping("/checkPassStat")
	public String checkPassForm(Model model)
	{
		return "checkPassStat";
	}
	
	@PostMapping("/checkPassStatus")
	public String seePassStatus(@ModelAttribute("pass") Citizen pass, Model model)
	{
		
		long id=pass.getId();
		Citizen passrequest=citizenService.getPassRequestById(id);
		String source_name=passrequest.getSource();
		String destination_name=passrequest.getDestination();
		List<StateWise> stateWiseList = statService.getStateWiseList();
		String s1=new String();
		String s2=new String();
		int source_count=0,destination_count=0;
		for(int i=0;i<stateWiseList.size();i++)
		{
			StateWise s=stateWiseList.get(i);
			if(s.getStateName()==source_name)
			{
				s1=s.getActive();
				source_count=Integer.parseInt(s1);
				continue;
			}
			if(s.getStateName()==destination_name)
			{
				s2=s.getActive();
				destination_count=Integer.parseInt(s2);
				continue;
			}
		}
		if(source_count<=100000 && destination_count<=100000)
		{
			passrequest.setStatus("confirmed");
			citizenService.saveCitizen(passrequest);
		}
		else
		{
			passrequest.setStatus("denied");
			citizenService.saveCitizen(passrequest);
		}
		pass.setFirstName(passrequest.getFirstName());
		pass.setLastName(passrequest.getLastName());
		pass.setPhoneNo(passrequest.getPhoneNo());
		pass.setStatus(passrequest.getStatus());
		pass.setSource(passrequest.getSource());
		pass.setDestination(passrequest.getDestination());
		model.addAttribute(pass);
		return "checkPassStat";
	}
	
}
