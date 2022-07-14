package com.sid.dashboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.sid.dashboard.client.CategoryClient;
import com.sid.dashboard.client.InfluenceClient;
import com.sid.dashboard.dto.AddCategoryDTO;
import com.sid.dashboard.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manageCategory")
public class CategoryController {
	
	@Autowired
	private CategoryClient client;
	
	@Autowired
	private InfluenceClient influenceClient;
	
	private static List<Map<String, Object>> allCategories = new ArrayList<>();
	private static List<Map<String, String>> allInfluences = new ArrayList<>();
	
	@GetMapping("/addEditCategory")
	public ModelAndView createAddEditCategoryView(){
		ModelAndView modelAndView = new ModelAndView("views/add-edit-category");
		List<Map<String, String>> influences = influenceClient.fetchInfluencesClient(null, "influence/fetchAllInfluences");
		List<Map<String, Object>> categories = client.fetchCategoriesClient(null, "category/fetchAllCategories");
		allCategories = categories;
		allInfluences = influences;
		modelAndView.addObject("influences", influences);
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("action", "Save");
		modelAndView.addObject("addCategoryDTO", new AddCategoryDTO());
		return modelAndView;
	}
	
	@PostMapping("/addEditCategory/addCategory")
	public Object addCategory(@Valid AddCategoryDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView();
		
		if(result.hasErrors()){
			modelAndView.setViewName("views/add-edit-category");
			modelAndView.addObject("isError", "Yes");
			modelAndView.addObject("action", "Save");
			modelAndView.addObject("message", "ERROR: Unable to add a category.");
			modelAndView.addObject("influences", allInfluences);
			modelAndView.addObject("categories", allCategories);
			return modelAndView;
		}
		
		Map<String, Object> requestBody = new HashMap<>();
		
		requestBody.put("category_name", dto.getCategoryName());
		requestBody.put("description", dto.getDescription());
		requestBody.put("influence_ids", dto.getInfluenceIds());
		
		Response response = client.addCategoryClient(requestBody, "category/addCategory");
		
		return "redirect:/manageCategory/addEditCategory";
	}

	@GetMapping("/addEditCategory/delete/{categoryId}")
	public Object deleteCategory(@PathVariable String categoryId){
		ModelAndView modelAndView = new ModelAndView();
		List<Map<String, Object>> requestBody = new ArrayList<>();
		Map<String, Object> category = new HashMap<>();
		category.put("category_id", categoryId);
		requestBody.add(category);
		Response response = client.removeCategoriesClient(requestBody, "category/removeCategories");
		
		return "redirect:/manageCategory/addEditCategory";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/addEditCategory/editView/{categoryId}")
	public Object editCategoryView(@PathVariable String categoryId){
		ModelAndView modelAndView = new ModelAndView("views/add-edit-category");
		
		
		for(Map<String, Object> category : allCategories){
			if(category.get("id").toString().equalsIgnoreCase(categoryId)){
				modelAndView.addObject("action", "Edit");
				modelAndView.addObject("influences", allInfluences);
				modelAndView.addObject("categories", allCategories);
				AddCategoryDTO dto =  new AddCategoryDTO();
				dto.setCategoryName(category.get("category").toString());
				dto.setDescription(category.get("description").toString());
				dto.setCategoryId(category.get("id").toString());
				System.out.println("Inf ids: "+category.toString());
				dto.setInfluenceIds((List<String>)category.get("influence_ids"));
				modelAndView.addObject("addCategoryDTO", dto);
				return modelAndView;
			}
			
		}

		return "redirect:/manageCategory/addEditCategory";
	}
	
	@PostMapping("/addEditCategory/editCategory")
	public Object editCategory(@Valid AddCategoryDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("Inside method---"+dto.getCategoryId());
		
		if(result.hasErrors()){
			modelAndView.setViewName("views/add-edit-category");
			modelAndView.addObject("influences", allInfluences);
			modelAndView.addObject("categories", allCategories);
			return modelAndView;
		}
		//List<Map<String, Object>> removeCategoriesRequest = new ArrayList<>();
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("category_id", dto.getCategoryId());
		requestBody.put("category_name", dto.getCategoryName());
		requestBody.put("description", dto.getDescription());
		requestBody.put("influence_ids", dto.getInfluenceIds());
		
		Map<String, Object> category = new HashMap<>();
		category.put("category_id", dto.getCategoryId());
		
		//removeCategoriesRequest.add(category);
		
		//client.removeCategoriesClient(removeCategoriesRequest, "category/removeCategories");
		
		client.updateCategoryClient(requestBody, "category/updateCategory");
		
		return "redirect:/manageCategory/addEditCategory";
	}
	
	/*@GetMapping("/categoryPositions")
	public ModelAndView createcategoryPositionsView(){
		ModelAndView modelAndView = new ModelAndView("views/category-positions");
		return modelAndView;
	}*/

}
