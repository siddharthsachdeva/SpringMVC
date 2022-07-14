package com.sid.dashboard.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.sid.dashboard.dto.ReviewFilterDTO;

@Component
public class ReviewModel {

	public Set<Map<String, Object>> searchReviews(ReviewFilterDTO dto, List<Map<String, Object>> allReviews) {

		String firstName = dto.getFirstName();
		String email = dto.getEmail();
		String company = dto.getCompany();
		String location = dto.getLocation();
		String ratingCriteria = dto.getRatingCriteria();
		String reviewData = dto.getReview();
		String category = dto.getCategory();
		String country = dto.getCountry();

		Set<Map<String, Object>> filteredUsers = new HashSet<>();

		for (Map<String, Object> review : allReviews) {

			if (firstName != null && !firstName.isEmpty()) {
				// As contains is case sensitive.
				if (review.get("first_name").toString().toLowerCase().contains(firstName.toLowerCase())) {
					filteredUsers.add(review);
				}
			}

			if (email != null && !email.isEmpty()) {
				// As contains is case sensitive.
				if (review.get("email").toString().toLowerCase().contains(email.toLowerCase())) {
					filteredUsers.add(review);
				}
			}

			if (company != null && !company.isEmpty()) {
				if (review.get("company_name").toString().toLowerCase().contains(company.toLowerCase())) {
					// As filteredUsers is a set I don't have to check before
					// adding whether it is existing or not.
					filteredUsers.add(review);
				}
			}

			if (location != null && !location.isEmpty()) {
				if (review.get("location").toString().toLowerCase().contains(location.toLowerCase())) {
					// As filteredUsers is a set I don't have to check before
					// adding whether it is existing or not.
					filteredUsers.add(review);
				}
			}

			if (reviewData != null && !reviewData.isEmpty()) {
				if (review.get("review").toString().toLowerCase().contains(reviewData.toLowerCase())) {
					filteredUsers.add(review);
				}
			}
			
			if (category != null && !category.isEmpty()) {
				if (review.get("company_categories").toString().toLowerCase().contains(category.toLowerCase())) {
					filteredUsers.add(review);
				}
			}
			
			if (country != null && !country.isEmpty()) {
				if (review.get("country").toString().toLowerCase().contains(country.toLowerCase())) {
					filteredUsers.add(review);
				}
			}

			if (ratingCriteria != null && !ratingCriteria.isEmpty()) {
				
				if (Integer.parseInt(
						ratingCriteria.substring(2, ratingCriteria.length())) >= Integer
								.parseInt(review.get("rating").toString())) {

					// As filteredUsers is a set I don't have to check before
					// adding whether it is existing or not.
					filteredUsers.add(review);

				}
			}

		}
		return filteredUsers;
	}

	public Set<String> fetchLocationsFromCompanies(List<Map<String, Object>> companies) {
		
		Set<String> locations = new HashSet<>();
		
		for(Map<String, Object> company : companies){
			locations.add(company.get("city").toString());
		}
		
		return locations;
	}

	public Set<String> fetchCountriesFromCompanies(List<Map<String, Object>> companies) {

		Set<String> countries = new HashSet<>();
		
		for(Map<String, Object> company : companies){
			countries.add(company.get("country").toString());
		}
		
		return countries;
	}

}
