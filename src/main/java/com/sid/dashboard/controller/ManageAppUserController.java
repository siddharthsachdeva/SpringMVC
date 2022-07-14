package com.sid.dashboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.AppUserClient;
import com.sid.dashboard.dto.UserFilterDTO;
import com.sid.dashboard.model.AppUserModel;
import com.sid.dashboard.util.Constants;
import com.sid.dashboard.util.ExportExcelUtil;

@Controller
@RequestMapping("/manageAppUser")
public class ManageAppUserController {

	@Autowired
	private AppUserClient client;
	
	@Autowired
	private AppUserModel model;

	private static List<Map<String, String>> allUsers = new ArrayList<Map<String, String>>();
	
	private static List<Map<String, String>> allFeedbacks = new ArrayList<Map<String, String>>();

	@GetMapping("/viewUsers")
	public ModelAndView createViewUsersView() {
		ModelAndView modelAndView = new ModelAndView("views/view-users");
		List<Map<String, String>> users = client.fetchUsersClient(null, "user/fetchAllUsers");
		allUsers = users;
		
		modelAndView.addObject("message", "Manage Users.");
		modelAndView.addObject("users", users);
		modelAndView.addObject("genders", Constants.GENDER);
		modelAndView.addObject("statusList", Constants.ACCOUNT_STATUS);
		modelAndView.addObject("userFilterDTO", new UserFilterDTO());

		return modelAndView;
	}

	@GetMapping("/userFeedback")
	public ModelAndView createUserFeedbackView() {
		ModelAndView modelAndView = new ModelAndView("views/user-feedback");
		List<Map<String, String>> userFeedbacks = client.fetchAppFeedbackClient(null, "feedback/fetchAppFeedback");
		modelAndView.addObject("message", "View user feedbacks.");
		allFeedbacks = userFeedbacks;
		modelAndView.addObject("userFeedbacks", userFeedbacks);
		modelAndView.addObject("genders", Constants.GENDER);
		modelAndView.addObject("statusList", Constants.ACCOUNT_STATUS);
		modelAndView.addObject("userFilterDTO", new UserFilterDTO());
		
		return modelAndView;
	}

	@GetMapping("/manageIssueType")
	public ModelAndView createManageIssueTypeView() {
		ModelAndView modelAndView = new ModelAndView("views/manage-issue-type");
		return modelAndView;
	}

	@GetMapping("/userIssue")
	public ModelAndView createUserIssueView() {
		ModelAndView modelAndView = new ModelAndView("views/user-issue");
		return modelAndView;
	}

	@GetMapping("/userFeedback/searchUserFeedbacks")
	public ModelAndView searchUserFeedbacks(UserFilterDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/user-feedback");
		
		String firstName = dto.getFirstName();
		String email = dto.getEmail();
		String city = dto.getCity();
		String state = dto.getState();
		String lastName = dto.getLastName();
		String country = dto.getCountry();
		String zipcode = dto.getZipcode();
		String gender = dto.getGender();
		String accountStatus = dto.getAccountStatus();

		if ((firstName == null || firstName.isEmpty()) && (email == null || email.isEmpty())
				&& (city == null || city.isEmpty()) && (state == null || state.isEmpty()) &&
				(zipcode == null || zipcode.isEmpty()) &&
				(lastName == null || lastName.isEmpty() && (gender == null || gender.isEmpty()) &&
				(country == null || country.isEmpty()) && (accountStatus == null || accountStatus.isEmpty()))) {

			modelAndView.addObject("userFeedbacks", allFeedbacks);

		} else {

			Set<Map<String, String>> filteredUserFeedbacks = model.searchUsers(dto, allFeedbacks);

			modelAndView.addObject("userFeedbacks", filteredUserFeedbacks);

		}
		
		modelAndView.addObject("genders", Constants.GENDER);
		modelAndView.addObject("statusList", Constants.ACCOUNT_STATUS);

		return modelAndView;
	}
	
	@GetMapping("/viewUsers/searchUsers")
	public ModelAndView searchUsers(UserFilterDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/view-users");
		
		String firstName = dto.getFirstName();
		String email = dto.getEmail();
		String city = dto.getCity();
		String state = dto.getState();
		String lastName = dto.getLastName();
		String country = dto.getCountry();
		String zipcode = dto.getZipcode();
		String gender = dto.getGender();
		String accountStatus = dto.getAccountStatus();

		if ((firstName == null || firstName.isEmpty()) && (email == null || email.isEmpty())
				&& (city == null || city.isEmpty()) && (state == null || state.isEmpty()) &&
				(zipcode == null || zipcode.isEmpty()) &&
				(lastName == null || lastName.isEmpty() && (gender == null || gender.isEmpty()) &&
				(country == null || country.isEmpty()) && (accountStatus == null || accountStatus.isEmpty()))) {

			modelAndView.addObject("users", allUsers);

		} else {

			Set<Map<String, String>> filteredUsers = model.searchUsers(dto, allUsers);

			modelAndView.addObject("users", filteredUsers);

		}
		
		modelAndView.addObject("genders", Constants.GENDER);
		modelAndView.addObject("statusList", Constants.ACCOUNT_STATUS);

		return modelAndView;
	}

	
	@GetMapping("/viewUsers/toggleAccountStatus/{id}")
	public String toggleAccountStatus(@PathVariable String id) {
		Map<String, String> request = new HashMap<>();
		request.put("user_id", id);
		client.toggleAccountStatus(request, "user/toggleAccountStatus");
		return "redirect:/manageAppUser/viewUsers";
	}
	
	@GetMapping("/viewUsers/toggleOptOutStatus/{id}")
	public String toggleOptOutStatus(@PathVariable String id) {
		Map<String, String> request = new HashMap<>();
		request.put("user_id", id);
		client.toggleOptOutStatus(request, "user/toggleOptOutStatus");
		return "redirect:/manageAppUser/viewUsers";
	}
	
	@GetMapping("/report")
	public ModelAndView report(HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("type");
		if(type.equalsIgnoreCase("xsl")){
			return new ModelAndView( new ExportExcelUtil(), "data", allUsers);
		}
		return new ModelAndView("views/view-users", "users", allUsers);
	}

}
