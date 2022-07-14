package com.sid.dashboard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.CategoryClient;
import com.sid.dashboard.client.CompanyClient;
import com.sid.dashboard.client.ReviewClient;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.dto.ReviewFilterDTO;
import com.sid.dashboard.dto.ReviewSortDTO;
import com.sid.dashboard.model.ReviewModel;
import com.sid.dashboard.util.ExportExcelUtil;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewClient client;
	
	@Autowired
	private CompanyClient companyClient;
	
	@Autowired
	private CategoryClient categoryClient;
	
	@Autowired
	private ReviewModel model;
	
	private static List<Map<String, Object>> allReviews = new ArrayList<>();
	
	private static List<Map<String, Object>> allCompanies = new ArrayList<>();
	
	private static List<Map<String, Object>> allCategories = new ArrayList<>();
	
	private static List<Map<String, Object>> currentTableData = new ArrayList<>();
	
	private static Set<String> allLocations = new HashSet<>();
	private static Set<String> allCountries = new HashSet<>();

	@GetMapping("/reviewLimit")
	public ModelAndView createReviewLimitView(){
		ModelAndView modelAndView = new ModelAndView("views/review-limit");
		return modelAndView;
	}
	
	@GetMapping("/userReviews")
	public ModelAndView createReviewsView(){
		ModelAndView modelAndView = new ModelAndView("views/user-reviews");
		List<Map<String, Object>> reviews = client.fetchAllUsersReviewsDetails(null, "survey/fetchAllUsersReviewsDetails");
		List<Map<String, Object>> companies = companyClient.fetchAllCompaniesClient(null, "company/fetchAllCompanies");
		List<Map<String, Object>> categories = categoryClient.fetchCategoriesClient(null,
				"category/fetchAllCategories");
		
		Set<String> locations = model.fetchLocationsFromCompanies(companies);
		Set<String> countries = model.fetchCountriesFromCompanies(companies);
		
		allCategories = categories;
		allCompanies = companies;
		allReviews = reviews;
		allLocations = locations;
		currentTableData = allReviews;
		allCountries = countries;
		modelAndView.addObject("reviewFilterDTO", new ReviewFilterDTO());
		modelAndView.addObject("reviewSortDTO", new ReviewSortDTO());
		modelAndView.addObject("message", "Manage Reviews");
		modelAndView.addObject("companies", companies);
		modelAndView.addObject("reviews", reviews);
		modelAndView.addObject("locations", locations);
		modelAndView.addObject("countries", countries);
		modelAndView.addObject("categories", allCategories);
		
		return modelAndView;
	}
	
	@GetMapping("/report")
	public ModelAndView report(HttpServletRequest request, HttpServletResponse response){
		List<Map<String, Object>> excelData = new ArrayList<>();
		
		for(Map<String, Object> map : currentTableData){
			for(String key : map.keySet()){
				if(key.equalsIgnoreCase("company_categories")){
					String categories = map.get(key).toString();
					map.remove(key);
					map.put(key, categories);	
				}
			}
			excelData.add(map);
		}
		
		String type = request.getParameter("type");
		if(type.equalsIgnoreCase("xsl")){
			return new ModelAndView( new ExportExcelUtil(), "data", excelData);
		}
		return new ModelAndView("views/user-reviews", "reviews", currentTableData);
	}
	
	
	@GetMapping("/userReviews/searchReviews")
	public ModelAndView searchReviews(ReviewFilterDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/user-reviews");
		
		String firstName = dto.getFirstName();
		String email = dto.getEmail();
		String company = dto.getCompany();
		String location = dto.getLocation();
		String ratingCriteria = dto.getRatingCriteria();
		String review = dto.getReview();
		String category = dto.getCategory();
		String country = dto.getCountry();

		if ((firstName == null || firstName.isEmpty()) && (email == null || email.isEmpty())
				&& (company == null || company.isEmpty()) && (location == null || location.isEmpty()) &&
				(ratingCriteria == null || ratingCriteria.isEmpty()) &&
				(review == null || review.isEmpty()) && (category == null || category.isEmpty()) && (country == null || country.isEmpty())) {

			modelAndView.addObject("reviews", allReviews);

		} else {

			Set<Map<String, Object>> filteredReviews = model.searchReviews(dto, allReviews);
			
			List<Map<String, Object>> tableData = new ArrayList<>();
			for(Map<String, Object> data : filteredReviews){
				tableData.add(data);
			}
			currentTableData = tableData;
			modelAndView.addObject("reviews", filteredReviews);

		}
		
		modelAndView.addObject("reviewSortDTO", new ReviewSortDTO());
		modelAndView.addObject("message", "Manage Reviews");
		modelAndView.addObject("companies", allCompanies);
		modelAndView.addObject("categories", allCategories);
		modelAndView.addObject("locations", allLocations);
		modelAndView.addObject("countries", allCountries);

		return modelAndView;
	}
	
	@PostMapping("/userReviews/sortReviews")
	public Object sortReviews(ReviewSortDTO dto){
		ModelAndView modelAndView = new ModelAndView("views/user-reviews");
		
		System.out.println("Rating order: "+dto.getRatingOrder());
		System.out.println("Date order: "+dto.getDateOrder());
		
		if(dto.getRatingOrder().equalsIgnoreCase("Ascending")){
			Comparator<Map<String, Object>> mapComparator = new Comparator<Map<String, Object>>() {
			    public int compare(Map<String, Object> m1, Map<String, Object> m2) {
			        return Integer.valueOf(m2.get("rating").toString()).compareTo(Integer.valueOf(m1.get("rating").toString()));
			    }
			};
			Collections.sort(currentTableData, mapComparator);
		}else if(dto.getRatingOrder().equalsIgnoreCase("Descending")){
			Comparator<Map<String, Object>> mapComparator = new Comparator<Map<String, Object>>() {
			    public int compare(Map<String, Object> m1, Map<String, Object> m2) {
			        return Integer.valueOf(m1.get("rating").toString()).compareTo(Integer.valueOf(m2.get("rating").toString()));
			    }
			};
			Collections.sort(currentTableData, mapComparator);
		}else{
			return "redirect:/review/userReviews/";
		}
		
		modelAndView.addObject("reviewFilterDTO", new ReviewFilterDTO());
		modelAndView.addObject("reviews", currentTableData);
		modelAndView.addObject("message", "Manage Reviews");
		modelAndView.addObject("companies", allCompanies);
		modelAndView.addObject("categories", allCategories);
		modelAndView.addObject("locations", allLocations);
		modelAndView.addObject("countries", allCountries);
		return modelAndView;
	}
	
	@GetMapping("/userReviews/delete/{reviewId}")
	public Object deleteCategory(@PathVariable String reviewId){
		ModelAndView modelAndView = new ModelAndView();
		List<Map<String, Object>> requestBody = new ArrayList<>();
		Map<String, Object> category = new HashMap<>();
		category.put("review_id", reviewId);
		requestBody.add(category);
		Response response = client.removeReviewsClient(requestBody, "survey/removeReviews");
		
		return "redirect:/review/userReviews";
	}	
}
