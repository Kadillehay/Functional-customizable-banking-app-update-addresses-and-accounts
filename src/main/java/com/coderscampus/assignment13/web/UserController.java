package com.coderscampus.assignment13.web;

import java.util.Arrays;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;
import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {

		model.put("user", new User());

		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
	
		Address address = addressService.findAddressById(user);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		model.put("address", address);
		
		

		return "userdetails";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser(User user, Address address, HttpServletRequest request) {
		System.out.println(address);
//		User foundUser = userService.findById(user.getUserId());
//
//		foundUser.setName(user.getName());
//		String password = request.getParameter("password");
//		foundUser.setPassword(password);
//		foundUser.setUsername(user.getUsername());

		userService.saveUser(user, address);
		return "redirect:/users/" + user.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
}
