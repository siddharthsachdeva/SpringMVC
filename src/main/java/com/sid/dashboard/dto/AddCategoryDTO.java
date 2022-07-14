package com.sid.dashboard.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AddCategoryDTO {
	
	private String categoryId;
	
	@NotNull
	@NotEmpty
	private String categoryName;
	
	@NotNull
	@NotEmpty
	private String description;
	
	@NotNull
	@NotEmpty
	private List<String> influenceIds;
	
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getInfluenceIds() {
		return influenceIds;
	}
	public void setInfluenceIds(List<String> influenceIds) {
		this.influenceIds = influenceIds;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((influenceIds == null) ? 0 : influenceIds.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddCategoryDTO other = (AddCategoryDTO) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (influenceIds == null) {
			if (other.influenceIds != null)
				return false;
		} else if (!influenceIds.equals(other.influenceIds))
			return false;
		return true;
	}
	
}
