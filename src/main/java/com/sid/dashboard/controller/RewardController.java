package com.sid.dashboard.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.sid.dashboard.util.CustomCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.RewardClient;
import com.sid.dashboard.dto.AddRewardDTO;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.dto.RewardFilterDTO;
import com.sid.dashboard.util.Attribute;
import com.sid.dashboard.util.CloudnaryUtil;
import com.sid.dashboard.util.Constants;
import com.sid.dashboard.util.Message;

@Controller
@RequestMapping("/reward")
public class RewardController {
	
	@Autowired
	private RewardClient client;
	
	private static List<Map<String, Object>> allRewards = new ArrayList<>();
	private static List<String> companyNames = new ArrayList<>();
	private static List<String> rewardNames = new ArrayList<>();
	
	@GetMapping("/manageRewards")
	public ModelAndView createManageRewardView() {
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");
		modelAndView.addObject(Attribute.MESSAGE, Message.MANAGE_REWARDS);
		List<Map<String, Object>> rewards = client.fetchAllRewards(null, "reward/fetchAllRewards");
		allRewards = rewards;
		companyNames = rewards.stream().map(r ->  r.get(Constants.COMPANY).toString()).collect(Collectors.toList());
		rewardNames = rewards.stream().map(r -> r.get(Constants.NAME).toString()).collect(Collectors.toList());
		modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
		modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
		modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
		modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
		modelAndView.addObject(Attribute.REWARDS, rewards);
		modelAndView.addObject(Attribute.REWARD_FILTER_DTO, new RewardFilterDTO());
		modelAndView.addObject(Attribute.ADD_REWARD_DTO, new AddRewardDTO());
		return modelAndView;
	}
	
	@GetMapping("/manageRewards/search")
	public ModelAndView searchRewards(RewardFilterDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");
		modelAndView.addObject(Attribute.MESSAGE, Message.MANAGE_REWARDS);
		modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
		modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
		modelAndView.addObject(Attribute.REWARD_FILTER_DTO, dto);
		
		List<Map<String, Object>> filteredRewards = allRewards.stream()
				.filter(r -> (dto.getName().isEmpty() ? r.get(Constants.COMPANY) != null : dto.getName().equalsIgnoreCase(r.get(Constants.NAME).toString()) ))
				.filter(r -> (!dto.getType().isEmpty() ? dto.getType().equalsIgnoreCase(r.get(Constants.TYPE).toString()) : r.get(Constants.TYPE) != null))
				.filter(r -> (!dto.getStatus().isEmpty() ? dto.getStatus().equalsIgnoreCase(r.get(Constants.STATUS).toString()) : r.get(Constants.STATUS) != null))
				.filter(r -> (!dto.getCompanyName().isEmpty() ? dto.getCompanyName().equalsIgnoreCase(r.get(Constants.COMPANY).toString()) : r.get(Constants.COMPANY) != null))
				.collect(Collectors.toList());
		
		modelAndView.addObject(Attribute.REWARDS, filteredRewards);
		modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
		modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
		modelAndView.addObject(Attribute.ADD_REWARD_DTO, new AddRewardDTO());
		
		return modelAndView;
	}
	
	@PostMapping("/manageRewards/add")
	public Object addReward(@Valid AddRewardDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");
		
		if (dto.getIcon() == null || dto.getIcon().isEmpty()) {
			FieldError error = new FieldError(Attribute.ADD_REWARD_DTO, Constants.ICON,
					Message.REWARD_ICON_EMPTY);
			result.addError(error);
		}
		
		if(result.hasErrors()){
			modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
			modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
			modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
			modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
			modelAndView.addObject(Attribute.REWARDS, allRewards);
			modelAndView.addObject(Attribute.REWARD_FILTER_DTO, new RewardFilterDTO());
			modelAndView.addObject(Attribute.ADD_REWARD_DTO, dto);
			
			return modelAndView;
		}
		
		String url = null;
		
		try {
			File file = new File(dto.getIcon().getOriginalFilename());
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
			fos.write(dto.getIcon().getBytes());
			fos.close();
			CloudnaryUtil uploadUtil = new CloudnaryUtil();

			url = uploadUtil.uploadImage(file, dto.getName(), "rewards");

			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		LocalDateTime localDateTime = LocalDateTime.now();
		dto.setCreatedDate(dtf.format(localDateTime));
		
		dto.setIconUrl(url);
		dto.setIcon(null);
		List<AddRewardDTO> rewards = new ArrayList<>();
		rewards.add(dto);
		System.out.println(dto.toString());
		
		Response response = client.addReward(rewards, "reward/addRewards");
		
		return "redirect:/reward/manageRewards";
	}
	
	@GetMapping("/manageRewards/toggleRewardStatus/{id}")
	public String toggleAccountStatus(@PathVariable String id) {
		List<Map<String, String>> request = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put(Constants.REWARD_ID, id);
		request.add(map);
		client.toggleAccountStatus(request, "reward/toggleRewardsStatus");
		return "redirect:/reward/manageRewards/";
	}
	
	@GetMapping("/manageRewards/delete/{id}/{rewardName}")
	public Object deleteInfluence(@PathVariable String id, @PathVariable String rewardName) {
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");

		List<Map<String, String>> rewards = new ArrayList<>();
		Map<String, String> reward = new HashMap<>();
		reward.put(Constants.REWARD_ID, id);
		rewards.add(reward);

		Response response = client.deleteRewardsClient(rewards, "reward/deleteRewards");
		CloudnaryUtil util = new CloudnaryUtil();

		util.deleteImage(rewardName, "rewards");

		if (!response.getStatus().equalsIgnoreCase(Constants.OK)) {
			modelAndView.addObject(Attribute.MESSAGE, Message.UNABLE_TO_DELETE_REWARD);
			modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
			modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
			modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
			modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
			modelAndView.addObject(Attribute.REWARDS, allRewards);
			modelAndView.addObject(Attribute.REWARD_FILTER_DTO, new RewardFilterDTO());
			modelAndView.addObject(Attribute.ADD_REWARD_DTO, new AddRewardDTO());
			return modelAndView;
		}

		return "redirect:/reward/manageRewards/";
	}
	
	@GetMapping("/manageRewards/editView/{id}")
	public Object editRewardView(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");
		try{
		Map<String, Object> reward = allRewards.stream().filter(r -> r.get(Constants.ID).toString().equalsIgnoreCase(id)).collect(CustomCollector.singletonCollector());
		
		AddRewardDTO dto = new AddRewardDTO();
		
		dto.setId(id);
		dto.setName(reward.get(Constants.NAME).toString());
		dto.setCompany(reward.get(Constants.COMPANY).toString());
		dto.setDescription(reward.get(Constants.REWARD_DESCRIPTION).toString());
		dto.setValue(reward.get(Constants.REWARD_VALUE).toString());
		dto.setType(reward.get(Constants.TYPE).toString());
		dto.setValidFrom(reward.get(Constants.VALID_FROM).toString());
		dto.setValidTill(reward.get(Constants.VALID_TILL).toString());
		dto.setNoOfRewards(Integer.parseInt(reward.get(Constants.NO_OF_REWARDS).toString()));
		dto.setMinimumReviews(Integer.parseInt(reward.get(Constants.MINIMUM_REVIEWS).toString()));
		dto.setStatus(reward.get(Constants.STATUS).toString());
		dto.setIconUrl(reward.get(Constants.ICON_URL) == null ? "" : reward.get(Constants.ICON_URL).toString());
		dto.setCreatedDate(reward.get(Constants.CREATED_DATE).toString());
	
		modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
		modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
		modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
		modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
		modelAndView.addObject(Attribute.REWARDS, allRewards);
		modelAndView.addObject(Attribute.REWARD_FILTER_DTO, new RewardFilterDTO());
		modelAndView.addObject(Attribute.ADD_REWARD_DTO, dto);
		modelAndView.addObject(Attribute.ACTION, Constants.EDIT);
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject(Attribute.MESSAGE, Message.SOMETHING_WENT_WRONG);
			modelAndView.addObject(Attribute.STATUS, Constants.ERROR);
		}
		

		return modelAndView;
	}
	
	@PostMapping("/manageRewards/edit")
	public Object editReward(@Valid AddRewardDTO dto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView("views/manage-rewards");
		
		try{
		String url = null;
		if (dto.getIcon() == null || dto.getIcon().isEmpty()) {
			if(dto.getIconUrl()  == null || dto.getIconUrl().isEmpty()){
				FieldError error = new FieldError(Attribute.ADD_REWARD_DTO, Constants.ICON,
						Message.REWARD_ICON_EMPTY);
				result.addError(error);	
			}else{
				System.out.println("Use the old image i.e image is not updated: "+dto.getIconUrl());
				url = dto.getIconUrl();
			}
		}else{
			System.out.println("Image has been changed, therefore, delete the old image from cloud and upload new one and fetch its url.");
			// As uploading the product with same name, it should overwrite the old image.
			File file = new File(dto.getIcon().getOriginalFilename());
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(dto.getIcon().getBytes());
			fos.close();
			CloudnaryUtil uploadUtil = new CloudnaryUtil();
			url = uploadUtil.uploadImage(file, dto.getName(), "rewards");
			System.out.println(url);
			file.delete();
		}
		if(result.hasErrors()){
			modelAndView.addObject(Attribute.REWARD_NAMES, rewardNames);
			modelAndView.addObject(Attribute.COMPANY_NAMES, companyNames);
			modelAndView.addObject(Attribute.TYPES, Constants.REWARD_TYPES);
			modelAndView.addObject(Attribute.STATUS_LIST, Constants.REWARD_STATUS_LIST);
			modelAndView.addObject(Attribute.REWARDS, allRewards);
			modelAndView.addObject(Attribute.ACTION, Constants.EDIT);
			modelAndView.addObject(Attribute.REWARD_FILTER_DTO, new RewardFilterDTO());
			modelAndView.addObject(Attribute.ADD_REWARD_DTO, dto);
			
			return modelAndView;
		}
		dto.setIconUrl(url);
		dto.setIcon(null);
		
		Response response = client.updateReward(dto, "reward/updateReward");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:/reward/manageRewards";
	}
	


}
