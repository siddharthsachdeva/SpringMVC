package com.sid.dashboard.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddRewardDTO {
	
	@JsonProperty("reward_id")
	private String id;
	
	@NotNull
	@NotEmpty
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("reward_description")
	private String description;
	
	@NotNull
	@NotEmpty
	@JsonProperty("company")
	private String company;
	
	@NotNull
	@NotEmpty
	@JsonProperty("reward_value")
	private String value;
	
	@NotNull
	@NotEmpty
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("coupon_code")
	private String couponCode;
	
	@JsonProperty("offer_url")
	private String offerUrl;
	
	@NotNull
	@NotEmpty
	@JsonProperty("valid_from")
	private String validFrom;
	
	@NotNull
	@NotEmpty
	@JsonProperty("valid_till")
	private String validTill;
	
	@NotNull
	@NumberFormat
	@JsonProperty("no_of_rewards")
	private int noOfRewards;
	
	@NotNull
	@NumberFormat
	@JsonProperty("minimum_reviews")
	private int minimumReviews;
	
	@NotNull
	@NotEmpty
	@JsonProperty("status")
	private String status;
	
	@NotNull
	private MultipartFile icon;
	
	@JsonProperty("created_date")
	private String createdDate;
	
	@JsonProperty("icon_url")
	private String iconUrl;

	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTill() {
		return validTill;
	}
	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}
	public int getNoOfRewards() {
		return noOfRewards;
	}
	public void setNoOfRewards(int noOfRewards) {
		this.noOfRewards = noOfRewards;
	}
	public int getMinimumReviews() {
		return minimumReviews;
	}
	public void setMinimumReviews(int minimumReviews) {
		this.minimumReviews = minimumReviews;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public MultipartFile getIcon() {
		return icon;
	}
	public void setIcon(MultipartFile icon) {
		this.icon = icon;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getOfferUrl() {
		return offerUrl;
	}
	public void setOfferUrl(String offerUrl) {
		this.offerUrl = offerUrl;
	}
	@Override
	public String toString() {
		return "AddRewardDTO [id=" + id + ", name=" + name + ", description=" + description + ", company=" + company
				+ ", value=" + value + ", type=" + type + ", validFrom=" + validFrom + ", validTill=" + validTill
				+ ", noOfRewards=" + noOfRewards + ", minimumReviews=" + minimumReviews + ", status=" + status
				+ ", icon=" + icon + "]";
	}
}
