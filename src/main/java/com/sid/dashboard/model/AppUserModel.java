package com.sid.dashboard.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.sid.dashboard.dto.UserFilterDTO;

@Component
public class AppUserModel {
		
	public Set<Map<String, String>> searchUsers(UserFilterDTO dto, List<Map<String, String>> allUsers){
		
		String firstName = dto.getFirstName();
		String email = dto.getEmail();
		String city = dto.getCity();
		String state = dto.getState();
		String lastName = dto.getLastName();
		String country = dto.getCountry();
		String zipcode = dto.getZipcode();
		String gender = dto.getGender();
		String accountStatus = dto.getAccountStatus();
		
		Set<Map<String, String>> filteredUsers = new HashSet<>();
		
		for (Map<String, String> user : allUsers) {

			if (firstName != null && !firstName.isEmpty()) {
				//As contains is case sensitive.
				if (user.get("first_name").toLowerCase().contains(firstName.toLowerCase())) {
					filteredUsers.add(user);
				}
			}
			
			if (lastName != null && !lastName.isEmpty()) {
				//As contains is case sensitive.
				if (user.get("last_name").toLowerCase().contains(lastName.toLowerCase())) {
					filteredUsers.add(user);
				}
			}
			
			if (email != null && !email.isEmpty()) {
				if (user.get("email").toLowerCase().contains(email.toLowerCase())) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
			if (city != null && !city.isEmpty()) {
				if (user.get("city").toLowerCase().contains(city.toLowerCase())) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
			if (state != null && !state.isEmpty()) {
				if (user.get("state").toLowerCase().contains(state.toLowerCase())) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
			if (zipcode != null && !zipcode.isEmpty()) {
				if (user.get("zipcode").toLowerCase().contains(zipcode.toLowerCase())) {
					filteredUsers.add(user);
				}
			}
			
			if (gender != null && !gender.isEmpty()) {
				if (user.get("gender").toLowerCase().contains(gender.toLowerCase())) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
			if (country != null && !country.isEmpty()) {
				if (user.get("country").toLowerCase().contains(country.toLowerCase())) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
			if (accountStatus != null && !accountStatus.isEmpty()) {
				if (user.get("is_active").equalsIgnoreCase(accountStatus)) {
					//As filteredUsers is a set I don't have to check before adding whether it is existing or not.
					filteredUsers.add(user);
				}
			}
			
		}
		return filteredUsers;
	}

}
