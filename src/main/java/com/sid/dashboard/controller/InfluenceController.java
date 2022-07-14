package com.sid.dashboard.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sid.dashboard.client.InfluenceClient;
import com.sid.dashboard.dto.AddInfluenceDTO;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.model.InfluenceModel;
import com.sid.dashboard.util.Attribute;
import com.sid.dashboard.util.CloudnaryUtil;
import com.sid.dashboard.util.Constants;
import com.sid.dashboard.util.Message;

@Controller
@RequestMapping("/influence")
public class InfluenceController {

	@Autowired
	private InfluenceClient client;

	@Autowired
	private InfluenceModel model;

	public static List<Map<String, String>> allInfluences = new ArrayList<>();

	@GetMapping("/addEditInfluences")
	public ModelAndView createAddEditCategoryInfluencesView() {
		ModelAndView modelAndView = new ModelAndView("views/influence");
		List<Map<String, String>> influences = client.fetchInfluencesClient(null, "influence/fetchAllInfluences");
		allInfluences = influences;
		modelAndView.addObject("influences", influences);
		modelAndView.addObject("addInfluenceDTO", new AddInfluenceDTO());
		modelAndView.addObject("message", "Manage Influences");
		return modelAndView;
	}

	@PostMapping("/addEditInfluences/addInfluence")
	public Object addInfluence(@Valid AddInfluenceDTO influence, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/influence");
		try {

			if (influence.getInfluenceImage() == null || influence.getInfluenceImage().isEmpty()) {
				FieldError error = new FieldError("addInfluenceDTO", "influenceImage",
						"Influence Image should not be empty.");
				result.addError(error);
			}

			if (result.hasErrors()) {
				List<Map<String, String>> influences = client.fetchInfluencesClient(null,
						"influence/fetchAllInfluences");
				modelAndView.addObject("influences", influences);
				modelAndView.addObject("isError", "Yes");
				modelAndView.addObject("message", "Error: Unable to add the Influence.");
				return modelAndView;
			}

			File file = new File(influence.getInfluenceImage().getOriginalFilename());
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(influence.getInfluenceImage().getBytes());
			fos.close();
			CloudnaryUtil uploadUtil = new CloudnaryUtil();

			String url = uploadUtil.uploadImage(file, influence.getInfluenceName(), "influences");

			file.delete();

			List<Map<String, String>> request = new ArrayList<>();
			Map<String, String> influenceData = new HashMap<>();

			influenceData.put("influence", influence.getInfluenceName());
			influenceData.put("influence_image", url);

			request.add(influenceData);

			Response response = client.addAllInfluencesClient(request, "influence/addInfluences");
			String message;

			if (response.getStatus().equalsIgnoreCase(Constants.OK)) {
				message = Message.INFLUENCE_ADDED_SUCCESSFULLY;
			} else {
				message = Message.SOMETHING_WENT_WRONG;
			}
			modelAndView.addObject("message", message);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "redirect:/influence/addEditInfluences";
	}
	
	@PostMapping("/addEditInfluences/editInfluence")
	public Object editInfluence(@Valid AddInfluenceDTO influence, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/influence");
		String logoURL = null;
		try {
			
			if ((influence.getInfluenceImage() == null || influence.getInfluenceImage().isEmpty())) {
				if(influence.getImageUrl()  == null || influence.getImageUrl().isEmpty()){
					FieldError error = new FieldError("addInfluenceDTO", "influenceImage",
							"Influence Image should not be empty.");
					result.addError(error);	
				}else{
					System.out.println("Use the old image i.e image is not updated: "+influence.getImageUrl());
					logoURL = influence.getImageUrl();
				}
			}else{
				System.out.println("Image has been changed, therefore, delete the old image from cloud and upload new one and fetch its url.");
				// As uploading the product with same name, it should overwrite the old image.
				
				File file = new File(influence.getInfluenceImage().getOriginalFilename());
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(influence.getInfluenceImage().getBytes());
				fos.close();
				CloudnaryUtil uploadUtil = new CloudnaryUtil();
				logoURL = uploadUtil.uploadImage(file, influence.getInfluenceName(), "influences");
				System.out.println(logoURL);
				file.delete();
			}

			if (result.hasErrors()) {
				List<Map<String, String>> influences = client.fetchInfluencesClient(null,
						"influence/fetchAllInfluences");	
				modelAndView.addObject("influences", influences);
				modelAndView.addObject(Attribute.ACTION, Constants.EDIT);
				modelAndView.addObject("isError", "Yes");
				modelAndView.addObject("message", "Error: Unable to add the Influence.");
				return modelAndView;
			}
			
			Map<String, String> influenceMap = new HashMap<>();
			
			influenceMap.put("influence", influence.getInfluenceName());
			influenceMap.put("influence_id", influence.getInfluenceId());
			influenceMap.put("influence_image", logoURL);
			
			Response response = client.updateInfluence(influenceMap, "influence/updateInfluence");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/influence/addEditInfluences/";
	}

	@GetMapping("/addEditInfluences/delete/{id}/{influenceName}")
	public Object deleteInfluence(@PathVariable String id, @PathVariable String influenceName) {
		ModelAndView modelAndView = new ModelAndView("views/influence");

		List<Map<String, String>> influences = new ArrayList<>();
		Map<String, String> influence = new HashMap<>();
		influence.put("influence_id", id);
		influences.add(influence);

		Response response = client.removeInfluencesClient(influences, "influence/removeInfluences");

		CloudnaryUtil util = new CloudnaryUtil();

		util.deleteImage(influenceName, "influences");

		if (!response.getStatus().equalsIgnoreCase(Constants.OK)) {
			modelAndView.addObject("message", "Unable to delete the influence");
			return modelAndView;
		}

		return "redirect:/influence/addEditInfluences";
	}

	@GetMapping("/addEditInfluences/edit/{id}")
	public ModelAndView editInfluenceView(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("views/influence");
		try {
			Map<String, String> influence = model.fetchInfluenceById(allInfluences, id);
			System.out.println(influence.toString());
			AddInfluenceDTO dto = new AddInfluenceDTO();
			dto.setImageUrl(influence.get(Constants.INFLUENCE_IMAGE));
			dto.setInfluenceName(influence.get(Constants.INFLUENCE));
			dto.setInfluenceId(influence.get(Constants.ID));
			modelAndView.addObject(Attribute.ADD_INFLUENCE_DTO, dto);
			modelAndView.addObject(Attribute.INFLUENCES, allInfluences);
			modelAndView.addObject(Attribute.ACTION, Constants.EDIT);
			
			modelAndView.addObject(Attribute.STATUS, Constants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Attribute.MESSAGE, Message.SOMETHING_WENT_WRONG);
			modelAndView.addObject(Attribute.STATUS, Constants.ERROR);
		}
		return modelAndView;
	}

}
