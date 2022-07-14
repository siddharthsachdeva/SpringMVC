package com.sid.dashboard.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class InfluenceModel {
	
	public Map<String, String> fetchInfluenceById(List<Map<String, String>> allInfluences, String influenceId){
		for(Map<String, String> currentInfluence : allInfluences){
			if(currentInfluence.get("id").equalsIgnoreCase(influenceId)){
				return currentInfluence;
			}
		}
		return null;
	}

}
