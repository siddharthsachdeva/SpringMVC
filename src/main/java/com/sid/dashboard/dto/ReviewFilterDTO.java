package com.sid.dashboard.dto;

public class ReviewFilterDTO {
	
	private String firstName;
	private String email;
	private String company;
	private String location;
	private String ratingCriteria;
	private String review;
	private String category;
	private String country;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRatingCriteria() {
		return ratingCriteria;
	}
	public void setRatingCriteria(String ratingCriteria) {
		this.ratingCriteria = ratingCriteria;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
