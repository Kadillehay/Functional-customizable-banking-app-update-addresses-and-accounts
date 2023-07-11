package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;
	
	public List<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}
	
	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}
	
	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}
	
	public Account findAccountById(Long accountId) {
	    Optional<Account> accountOpt = accountRepo.findById(accountId);
	    return accountOpt.orElse(null);
	}
	public Account saveAccount(Account account) {
	    return accountRepo.save(account);
	}
	
	public Set<User> findAll() {
	    List<User> userList = new ArrayList<>(userRepo.findAllUsersWithAccountsAndAddresses());
	    Set<User> distinctUsers = new HashSet<>(userList);
	    return distinctUsers;
	}
//	public Set<User> findAll () {
//		return userRepo.findAllUsersWithAccountsAndAddresses();
//	}
	public Account createAccount(Long userId) {
	    User user = findById(userId);
	    
	    if (user != null) {
	        Account newAccount = new Account();
	        newAccount.setAccountName("New Account");
	        newAccount.getUsers().add(user);
	        
	        user.getAccounts().add(newAccount);
	        userRepo.save(user);
	        accountRepo.save(newAccount);
	        
	        return newAccount;
	    }
	    return null;
	}
	
	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		if (user.getUserId() == null) {
			Account checking = new Account();
			checking.setAccountName("Checking Account");
			checking.getUsers().add(user);
			Account savings = new Account();
			savings.setAccountName("Savings Account");
			savings.getUsers().add(user);
			
			user.getAccounts().add(checking);
			user.getAccounts().add(savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
			
		}
		
		return userRepo.save(user);
	}
	public void saveAddress(User user) {
	    User existingUser = userRepo.findById(user.getUserId()).orElse(null);
	    if (existingUser != null) {
	        Address newAddress = user.getAddress();
	        
	        Address existingAddress = existingUser.getAddress();
	        if (existingAddress == null) {
	            existingAddress = new Address();
	            existingUser.setAddress(existingAddress);
	        }
	        
	        if (newAddress.getAddressLine1() != null) {
	            existingAddress.setAddressLine1(newAddress.getAddressLine1());
	        }
	        if (newAddress.getAddressLine2() != null) {
	            existingAddress.setAddressLine2(newAddress.getAddressLine2());
	        }
	        if (newAddress.getCity() != null) {
	            existingAddress.setCity(newAddress.getCity());
	        }
	        if (newAddress.getRegion() != null) {
	            existingAddress.setRegion(newAddress.getRegion());
	        }
	        if (newAddress.getCountry() != null) {
	            existingAddress.setCountry(newAddress.getCountry());
	        }
	        if (newAddress.getZipCode() != null) {
	            existingAddress.setZipCode(newAddress.getZipCode());
	        }
	        
	        userRepo.save(existingUser);
	    }
	}
		

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
}
