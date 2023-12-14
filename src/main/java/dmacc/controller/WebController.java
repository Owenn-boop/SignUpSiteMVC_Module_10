package dmacc.controller;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.User;
import dmacc.repository.ContactRepository;

@Controller
public class WebController {
	@Autowired
	ContactRepository repo;

	@GetMapping({ "/", "viewAll" })
	public String viewAllUsers(Model model) {
		if (repo.findAll().isEmpty()) {
			return addNewUser(model);
		}
		model.addAttribute("users", repo.findAll());
		return "results";
	}

	@GetMapping("/inputUser")
	public String addNewUser(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "input";
	}

	@PostMapping("/inputUser")
	public String addNewUser(@ModelAttribute User u, Model model) {
		repo.save(u);
		return viewAllUsers(model);
	}

	@GetMapping("/edit/{id}")
	public String showUpdateUser(@PathVariable("id") long id, Model model) {
		User u = repo.findById(id).orElse(null);
		model.addAttribute("newUser", u);
		return "input";
	}

	@PostMapping("/update/{id}")
	public String reviseUser(User u, Model model) {
		if (u.getPassword().length() < 6) {
			model.addAttribute("error", "Password is too simple. \" " + u.getPassword() + " \"");
			return "error";
		}
		
		try {
			InternetAddress email = new InternetAddress(u.getEmail());
			email.validate();
		} catch (AddressException e) {
			model.addAttribute("error", "Invalid email address. \" " + u.getEmail() + " \"");
			return "error";
		}
		
		repo.save(u);
		return viewAllUsers(model);
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		User u = repo.findById(id).orElse(null);
		repo.delete(u);
		return viewAllUsers(model);
	}
}
