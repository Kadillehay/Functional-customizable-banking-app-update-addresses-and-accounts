package com.coderscampus.assignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class AccountController { 

	@Autowired
	private UserService userService;
	
	

	@GetMapping("users/{userId}/accounts")
	public String getCreateAccount(ModelMap model, @PathVariable Long userId) {
	    model.put("account", new Account());
	    model.put("userId", userId);
	    return "account"; 
	}
	

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String createAccount(@PathVariable Long userId, @PathVariable Long accountId) {
		Account createdAccount = userService.createAccount(userId);
		if (createdAccount != null) {
		} 
		return "redirect:/users/" + userId + "/accounts/" + createdAccount.getAccountId();
//		return "/users/" + userId + "/accounts/";	
	}
	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId, @ModelAttribute Account account) {
		Account existingAccount = userService.findAccountById(accountId);
	    
	    if (existingAccount != null) {
	        existingAccount.setAccountName(account.getAccountName());
	        
	        
	        userService.saveAccount(existingAccount);
	        
	        return "redirect:/users/" + userId + "/accounts/";
	    }
	    return "redirect:/error.html";
	}

	
}
