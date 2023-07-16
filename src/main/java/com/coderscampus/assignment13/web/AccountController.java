package com.coderscampus.assignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
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

	@PostMapping("users/{userId}/accounts")
	public String postCreateAccount(@PathVariable Long userId) {
		User user = userService.findById(userId);
		userService.createAccount(userId);

		return "redirect:/users/" + userId;

	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId,
			@ModelAttribute Account account) {
		Account existingAccount = userService.findAccountById(accountId);

		if (existingAccount != null) {
			existingAccount.setAccountName(account.getAccountName());

			userService.saveAccount(existingAccount);

			return "redirect:/users/" + userId + "/accounts/" + existingAccount.getAccountId();
		}
		return "redirect:/error.html";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getUserAccount(ModelMap modelMap, @PathVariable Long userId, @PathVariable Long accountId) {
		modelMap.put("user", this.userService.findById(userId));
		modelMap.put("account", userService.findAccountById(accountId));
		return "account";
	}

}
