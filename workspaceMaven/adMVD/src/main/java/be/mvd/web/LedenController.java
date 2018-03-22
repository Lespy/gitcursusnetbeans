package be.mvd.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leden")
public class LedenController {
	private static final String LEDEN_VIEW = "leden/";
	private static final String TOEVOEGEN_VIEW = "leden/toevoegen";
	private static final String LIJST_VIEW = "leden/lijst"; 
	
	@GetMapping
	String findAll(){
		return LEDEN_VIEW;
	}
	
	@GetMapping("toevoegen")
	String createForm(){
		return TOEVOEGEN_VIEW;
	}
	@GetMapping("lijst")
	String createForm1(){
		return LIJST_VIEW;
	}
}
