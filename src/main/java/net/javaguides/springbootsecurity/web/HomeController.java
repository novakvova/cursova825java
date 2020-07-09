package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.Role;
import net.javaguides.springbootsecurity.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springbootsecurity.entities.Message;
import net.javaguides.springbootsecurity.repositories.MessageRepository;
import net.javaguides.springbootsecurity.repositories.UserRepository;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class HomeController
{
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@GetMapping("/home")
	public String home(Model model)
	{
		model.addAttribute("msgs", messageRepository.findAll());		
		return "userhome";
	}
	
	@PostMapping("/messages")
	public String saveMessage(Message message)
	{
		messageRepository.save(message);
		return "redirect:/home";
	}
	@GetMapping("/register")
	public String GetRegister()
	{
		return "register";
	}
	@PostMapping("/register")
	public String Register(User user)
	{
		String pass = passwordEncoder.encode(user.getPassword());
		user.setPassword(pass);
		User u = new User();
		u.setPassword(pass);
		u.setEmail(user.getEmail());
		u.setName(user.getName());

//		List<Role> roles = new ArrayList<Role>();
//		Role role = new Role();
//		role.setId(3);
//		roles.add(role);
//		u.setRoles(roles);
		userRepository.save(u);
		//messageRepository.save(message);
		return "redirect:/home";
	}
}
