package com.sid.dashboard.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.CategoryClient;
import com.sid.dashboard.client.CompanyClient;
import com.sid.dashboard.client.InfluenceClient;
import com.sid.dashboard.client.UploadDataToDBClient;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.dto.UploadExcelDTO;
import com.sid.dashboard.model.UploadDataToDBModel;
import com.sid.dashboard.util.Constants;

@Controller
@RequestMapping("/upload")
public class UploadDataToDBController {

	@Autowired
	private UploadDataToDBModel model;
	
	@Autowired
	private UploadDataToDBClient client;
	
	@Autowired
	private InfluenceClient influenceClient;
	
	@Autowired
	private CategoryClient categoryClient;
	
	@Autowired
	private CompanyClient companyClient;

	@GetMapping("/excelToDB/")
	public ModelAndView excelToDB() {
		ModelAndView modelAndView = new ModelAndView("views/upload-excel-to-db");
		modelAndView.addObject("uploadExcelDTO", new UploadExcelDTO());
		return modelAndView;
	}

	@PostMapping("/excelToDB/uploadExcel")
	public Object uploadExcel(@Valid UploadExcelDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/upload-excel-to-db");
		System.out.println("Type: " + dto.getType());
		List<Map<String, String>> dataList;
		List<Map<String, Object>> categories;
		List<Map<String, Object>> companies;
		
		Response response;
		
		switch (dto.getType()) {
		case Constants.INFLUENCES:
			dataList = model.uploadInfluences(dto);
			response = influenceClient.addAllInfluencesClient(dataList, "influence/addInfluences");
			break;
		case Constants.CATEGORIES:
			categories = model.uploadCategories(dto);
			for(Map<String, Object> category : categories ){
				System.out.println(category.toString());	
			}
			
			response = categoryClient.addCategoriesClient(categories, "category/addCategories");
			break;
		case Constants.COMPANIES:
			System.out.println("I'm in companies case.");
			List<Map<String, Object>> allCategories = categoryClient.fetchCategoriesClient(null,
					"category/fetchAllCategories");
			companies = model.uploadCompanies(dto, allCategories);
			
			for(Map<String, Object> company : companies ){
				System.out.println(company.toString());	
			}
			
			response = companyClient.addCompaniesClient(companies, "company/addCompanies");
			break;
	}

		return "redirect:/upload/excelToDB/";
	}

}
