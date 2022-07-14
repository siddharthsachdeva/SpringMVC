package com.sid.dashboard.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.sid.dashboard.dto.CompanyFilterDTO;

@Component
public class CompanyModel {

	public Set<Map<String, Object>> searchCompanies(CompanyFilterDTO dto, List<Map<String, Object>> allCompanies) {
		
		String categoryName = dto.getCategoryName();
		String companyName = dto.getCompanyName();
		
		Set<Map<String, Object>> filteredCompanies = new HashSet<>();
		
		for(Map<String, Object> company : allCompanies){
			if (categoryName != null && !categoryName.isEmpty()) {
				//As contains is case sensitive.
				@SuppressWarnings("unchecked")
				List<String> categories = (List<String>) company.get("company_categories"); 
				if (categories.contains(categoryName)) {
					filteredCompanies.add(company);
				}
			}
			
			if (companyName != null && !companyName.isEmpty()) {
				//As contains is case sensitive.
				if (((String) company.get("name")).toLowerCase().contains(companyName.toLowerCase())) {
					filteredCompanies.add(company);
				}
			}
		}
		
		return filteredCompanies;
	}

}
