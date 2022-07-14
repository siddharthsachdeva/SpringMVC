package com.sid.dashboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.client.QuestionClient;
import com.sid.dashboard.dto.QuestionDTO;
import com.sid.dashboard.dto.QuestionsList;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.util.Constants;
import com.sid.dashboard.util.Message;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	QuestionClient client;

	@GetMapping("/")
	public ModelAndView createQuestionsView() {
		ModelAndView modelAndView = new ModelAndView("views/question");
		List<QuestionDTO> questions = client.fetchQuestionsClient(null, "question/fetchAllQuestions");
		QuestionsList questionsList = new QuestionsList();
		if(questions.size() >= 3){
			questionsList.setId1(questions.get(0).getId());
			questionsList.setQuestion1(questions.get(0).getDescription());
			questionsList.setId2(questions.get(1).getId());
			questionsList.setQuestion2(questions.get(1).getDescription());
			questionsList.setId3(questions.get(2).getId());
			questionsList.setQuestion3(questions.get(2).getDescription());	
		}
		modelAndView.addObject("message", "Manage Questions.");
		modelAndView.addObject("questionsList", questionsList);
		return modelAndView;
	}

	public void print(QuestionsList questionList) {
		System.out.println(questionList.getQuestion2());
	}

	@PostMapping("/save")
	public ModelAndView onSaveClick(QuestionsList questionsList, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("views/question");
		String message;
		Response response = client.deleteAllQuestionsClient(null, "question/deleteAllQuestions");

		if (response.getStatus().equalsIgnoreCase(Constants.OK)) {

			List<Map<String, String>> data = new ArrayList<>();
			
			Map<String, String> question1 = new HashMap<>();
			question1.put(Constants.QUESTION_DESCRIPTION, questionsList.getQuestion1());
			data.add(question1);
			
			Map<String, String> question2 = new HashMap<>();
			question2.put(Constants.QUESTION_DESCRIPTION, questionsList.getQuestion2());
			data.add(question2);
			
			Map<String, String> question3 = new HashMap<>();
			question3.put(Constants.QUESTION_DESCRIPTION, questionsList.getQuestion3());
			data.add(question3);
			
			
			Response addAllQuesResponse = client.addAllQuestionsClient(data, "question/addQuestions");

			if (addAllQuesResponse.getStatus().equalsIgnoreCase(Constants.OK)) {
				message = Message.QUESTIONS_UPDATED_SUCCESSFULLY;
			} else {
				message = Message.SOMETHING_WENT_WRONG;
			}

		} else {
			message = Message.SOMETHING_WENT_WRONG;
		}
		
		modelAndView.addObject("message", message);

		return modelAndView;
	}

}
