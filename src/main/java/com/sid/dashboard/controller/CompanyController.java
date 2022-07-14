package com.sid.dashboard.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.CategoryClient;
import com.sid.dashboard.client.CompanyClient;
import com.sid.dashboard.dto.AddCompanyDTO;
import com.sid.dashboard.dto.CompanyFilterDTO;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.model.CompanyModel;
import com.sid.dashboard.util.CloudnaryUtil;
import com.sid.dashboard.util.Constants;

@Controller
@RequestMapping("/manageCompany")
public class CompanyController {

	@Autowired
	CompanyClient client;

	@Autowired
	CategoryClient categoryClient;
	
	@Autowired
	private CompanyModel model;
	
	private static List<Map<String, Object>> allCompanies = new ArrayList<>();
	private static List<Map<String, Object>> allCategories = new ArrayList<>();

	@GetMapping("/addEditCompany")
	public ModelAndView createAddEditCompanyView() {
		ModelAndView modelAndView = new ModelAndView("views/add-edit-company");
		List<Map<String, Object>> companies = client.fetchAllCompaniesClient(null, "company/fetchAllCompanies");
		List<Map<String, Object>> categories = categoryClient.fetchCategoriesClient(null,
				"category/fetchAllCategories");
		allCompanies = companies;
		allCategories = categories;
		modelAndView.addObject("companies", companies);
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("action", "Save");
		modelAndView.addObject("addCompanyDTO", new AddCompanyDTO());
		modelAndView.addObject("companyFilterDTO", new CompanyFilterDTO());
		modelAndView.addObject("message", "Manage Companies.");

		return modelAndView;
	}

	@PostMapping("/addEditCompany/add")
	public Object addCompany(@Valid AddCompanyDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/add-edit-company");
		
		if(dto.getLogo() == null || dto.getLogo().isEmpty()){
			FieldError error = new FieldError("addCompanyDTO", "logo", "Logo should not be empty.");
			result.addError(error);
		}

		if (result.hasErrors()) {
			
			modelAndView.addObject("companies", allCompanies);
			modelAndView.addObject("categories", allCategories);
			modelAndView.addObject("isError", "Yes");
			modelAndView.addObject("action", "Save");
			modelAndView.addObject("companyFilterDTO", new CompanyFilterDTO());
			modelAndView.addObject("message", "ERROR: Unable to add a company");
			
			return modelAndView;
			
		}
		try {
			File file = new File(dto.getLogo().getOriginalFilename());
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(dto.getLogo().getBytes());
			fos.close();
			CloudnaryUtil uploadUtil = new CloudnaryUtil();

			String logoURL = uploadUtil.uploadImage(file, dto.getCompanyName(), "companies");

			file.delete();

			Map<String, Object> request = new HashMap<>();

			request.put("company_name", dto.getCompanyName());
			request.put("company_description", dto.getDescription());
			request.put("website", dto.getWebsite());
			request.put("phone_no", dto.getContactNo());
			request.put("additional_phone_no", dto.getAdditionalContactNo());
			request.put("email", dto.getEmail());
			request.put("fb_link", dto.getFbPageLink());
			request.put("twitter_link", dto.getTwitterPageLink());
			request.put("company_categories", dto.getCategoryIds());
			request.put("logo", logoURL);
			request.put("city", dto.getLocation());
			request.put("country", dto.getCountry());
			Response response = client.addCompanyClient(request, "company/addCompany");

			if (!response.getStatus().equalsIgnoreCase(Constants.OK)) {
				modelAndView.addObject("message", "Unable to add company.");
				return modelAndView;
			}

			modelAndView.addObject("message", "Manage Companies.");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/manageCompany/addEditCompany";
	}

	@GetMapping("/addEditCompany/delete/{id}/{companyName}")
	public Object deleteCompany(@PathVariable String id, @PathVariable String companyName) {
		ModelAndView modelAndView = new ModelAndView("views/influence");

		Map<String, String> company = new HashMap<>();
		company.put("company_id", id);

		Response response = client.removeCompanyClient(company, "company/removeCompany");
		
		CloudnaryUtil util = new CloudnaryUtil();
		
		util.deleteImage(companyName, "companies");

		if (!response.getStatus().equalsIgnoreCase(Constants.OK)) {
			modelAndView.addObject("message", "Unable to delete the company.");
			return modelAndView;
		}

		return "redirect:/manageCompany/addEditCompany";
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("/addEditCompany/editView/{companyId}/{companyName}")
	public Object editCompanyView(@PathVariable String companyId, @PathVariable String companyName){
		ModelAndView modelAndView = new ModelAndView("views/add-edit-company");
		
		
		for(Map<String, Object> company : allCompanies){
			if(company.get("id").toString().equalsIgnoreCase(companyId)){
				modelAndView.addObject("action", "Edit");
				modelAndView.addObject("companies", allCompanies);
				modelAndView.addObject("categories", allCategories);
				AddCompanyDTO dto =  new AddCompanyDTO();
				dto.setCompanyName(company.get("name").toString());
				dto.setDescription(company.get("description").toString());
				dto.setFbPageLink(company.get("fb_link").toString());
				dto.setTwitterPageLink(company.get("twitter_link").toString());
				dto.setContactNo(company.get("phone_no").toString());
				dto.setAdditionalContactNo(company.get("additional_phone_no").toString());
				dto.setWebsite(company.get("website").toString());
				dto.setEmail(company.get("email").toString());
				dto.setCompanyId(companyId);
				dto.setCountry(company.get("country").toString());
				dto.setLocation(company.get("city").toString());
				dto.setCategoryIds((List<String>)company.get("category_ids"));
				
				System.out.println("View: Company name: "+dto.getCompanyName());
				
				/*try {
					URL url = new URL(company.get("logo").toString());
					File file = Paths.get(url.toURI()).toFile();
					FileInputStream fis = new FileInputStream(file);
					 MultipartFile multipartFile = new MockMultipartFile("file",
					            file.getName(), "text/plain", IOUtils.toByteArray(fis));
					 
					 dto.setLogo(multipartFile);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
				
				modelAndView.addObject("addCompanyDTO", dto);
				modelAndView.addObject("companyFilterDTO", new CompanyFilterDTO());
				return modelAndView;
			}
			
		}

		return "redirect:/manageCompany/addEditCompany";
	}
	
	
	@PostMapping("/addEditCompany/editCompany")
	public Object editCompany(@Valid AddCompanyDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView();
		
		System.out.println("Inside edit company");
		
		if(dto.getLogo() == null || dto.getLogo().isEmpty()){
			FieldError error = new FieldError("addCompanyDTO", "logo", "Logo should not be empty.");
			result.addError(error);
		}
		
		if(result.hasErrors()){
			modelAndView.setViewName("views/add-edit-company");
			modelAndView.addObject("companies", allCompanies);
			modelAndView.addObject("categories", allCategories);
			return modelAndView;
		}

		/*
		Map<String, String> removeCompanyRequest = new HashMap<>();
		removeCompanyRequest.put("company_id", dto.getCompanyId());*/

		try{
		
		File file = new File(dto.getLogo().getOriginalFilename());
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(dto.getLogo().getBytes());
		fos.close();
		
		
		CloudnaryUtil uploadUtil = new CloudnaryUtil();

		String logoURL = uploadUtil.uploadImage(file, dto.getCompanyName(), "companies");

		file.delete();

		Map<String, Object> updateCompanyRequest = new HashMap<>();
		System.out.println("Company id: "+dto.getCompanyId());
		System.out.println("Company Name: "+dto.getCompanyName());
		updateCompanyRequest.put("company_id", dto.getCompanyId());
		updateCompanyRequest.put("company_name", dto.getCompanyName());
		updateCompanyRequest.put("company_description", dto.getDescription());
		updateCompanyRequest.put("website", dto.getWebsite());
		updateCompanyRequest.put("phone_no", dto.getContactNo());
		updateCompanyRequest.put("additional_phone_no", dto.getAdditionalContactNo());
		updateCompanyRequest.put("email", dto.getEmail());
		updateCompanyRequest.put("fb_link", dto.getFbPageLink());
		updateCompanyRequest.put("twitter_link", dto.getTwitterPageLink());
		updateCompanyRequest.put("company_categories", dto.getCategoryIds());
		updateCompanyRequest.put("logo", logoURL);

		client.updateCompanyClient(updateCompanyRequest, "company/updateCompanyDetails");
		
		//client.addCompanyClient(updateCompanyRequest, "company/addCompany");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:/manageCompany/addEditCompany";
	}
	
	
	@GetMapping("/addEditCompany/searchCompanies")
	public ModelAndView searchCompanies(CompanyFilterDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView("views/add-edit-company");
		
		String categoryId = dto.getCategoryName();
		String companyName = dto.getCompanyName();
		
		if((categoryId == null || categoryId.isEmpty()) && (companyName == null || companyName.isEmpty())){
			modelAndView.addObject("companies", allCompanies);
		} else {
			Set<Map<String, Object>> filteredCompanies = model.searchCompanies(dto, allCompanies);
			modelAndView.addObject("companies", filteredCompanies);
		}
		
		modelAndView.addObject("categories", allCategories);
		modelAndView.addObject("addCompanyDTO", new AddCompanyDTO());
		modelAndView.addObject("action", "Save");
		
		return modelAndView;
	}
	
}
