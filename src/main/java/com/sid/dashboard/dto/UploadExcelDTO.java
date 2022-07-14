package com.sid.dashboard.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class UploadExcelDTO {
	
	@NotNull
	@NotEmpty
	private String type;
	
	@NotNull
	private MultipartFile excelFile;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MultipartFile getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}
}
