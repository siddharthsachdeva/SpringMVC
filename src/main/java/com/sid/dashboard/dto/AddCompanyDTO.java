package com.sid.dashboard.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class AddCompanyDTO {

	private String companyId;
	private String country;
	@NotNull
	@NotEmpty
	private List<String> categoryIds;
	@NotNull
	@NotEmpty
	private String companyName;
	private String description;
	private String fbPageLink;
	private String twitterPageLink;
	private String contactNo;
	private String additionalContactNo;
	private String website;
	private String email;
	private String location;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	private MultipartFile logo;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public List<String> getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFbPageLink() {
		return fbPageLink;
	}
	public void setFbPageLink(String fbPageLink) {
		this.fbPageLink = fbPageLink;
	}
	public String getTwitterPageLink() {
		return twitterPageLink;
	}
	public void setTwitterPageLink(String twitterPageLink) {
		this.twitterPageLink = twitterPageLink;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getAdditionalContactNo() {
		return additionalContactNo;
	}
	public void setAdditionalContactNo(String additionalContactNo) {
		this.additionalContactNo = additionalContactNo;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MultipartFile getLogo() {
		return logo;
	}
	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}
}
