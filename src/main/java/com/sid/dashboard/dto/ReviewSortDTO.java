package com.sid.dashboard.dto;

public class ReviewSortDTO {
	
	private String ratingOrder;
	private String dateOrder;
	public String getRatingOrder() {
		return ratingOrder;
	}
	public void setRatingOrder(String ratingOrder) {
		this.ratingOrder = ratingOrder;
	}
	public String getDateOrder() {
		return dateOrder;
	}
	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}
}
