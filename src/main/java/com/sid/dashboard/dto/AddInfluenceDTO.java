package com.sid.dashboard.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class AddInfluenceDTO {
	
	private String influenceId;
	
	@NotNull
	@NotEmpty
	private String influenceName;
	
	@NotNull
	private MultipartFile influenceImage;
	
	private String imageUrl;

	@Override
	public String toString() {
		return "AddInfluenceDTO [influenceId=" + influenceId + ", influenceName=" + influenceName + ", influenceImage="
				+ influenceImage + ", imageUrl=" + imageUrl + "]";
	}
	public String getInfluenceId() {
		return influenceId;
	}
	public void setInfluenceId(String influenceId) {
		this.influenceId = influenceId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getInfluenceName() {
		return influenceName;
	}
	public void setInfluenceName(String influenceName) {
		this.influenceName = influenceName;
	}
	public MultipartFile getInfluenceImage() {
		return influenceImage;
	}
	public void setInfluenceImage(MultipartFile influenceImage) {
		this.influenceImage = influenceImage;
	}
}
