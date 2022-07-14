package com.sid.dashboard.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sid.dashboard.client.InfluenceClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.sid.dashboard.dto.UploadExcelDTO;
import com.sid.dashboard.util.Constants;
import com.sid.dashboard.util.CustomCollector;

@Component
public class UploadDataToDBModel {

	@SuppressWarnings("resource")
	public List<Map<String, String>> uploadInfluences(UploadExcelDTO dto) {
		List<Map<String, String>> influences = new ArrayList<>();
		try {

			Workbook offices;

			if (dto.getExcelFile().getOriginalFilename().endsWith(".xlsx")) {
				offices = new XSSFWorkbook(dto.getExcelFile().getInputStream());
			} else {
				offices = new HSSFWorkbook(dto.getExcelFile().getInputStream());
			}

			Sheet worksheet = offices.getSheetAt(0);
			List<String> columnNames = new ArrayList<>();
			for (Cell cellColumnHeadings : worksheet.getRow(0)) {
				System.out.println(cellColumnHeadings.getStringCellValue() + "##");
				columnNames.add(cellColumnHeadings.getStringCellValue());
			}

			worksheet.removeRow(worksheet.getRow(0));

			for (Row row : worksheet) {

				for (Cell cell : row) {
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting
					// from version 4.0
					if (cell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(cell.getStringCellValue() + "--");
						String influence = cell.getStringCellValue();
						Map<String, String> map = new HashMap<>();
						map.put("influence", influence);
						map.put("influence_image", "");
						influences.add(map);
					}
				}
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return influences;
	}

	public List<Map<String, Object>> uploadCompanies(UploadExcelDTO dto, List<Map<String, Object>> allCategories) {
		List<Map<String, Object>> companies = new ArrayList<>();
		try {

			Workbook offices;

			if (dto.getExcelFile().getOriginalFilename().endsWith(".xlsx")) {
				offices = new XSSFWorkbook(dto.getExcelFile().getInputStream());
			} else {
				offices = new HSSFWorkbook(dto.getExcelFile().getInputStream());
			}

			Sheet worksheet = offices.getSheetAt(0);
			List<String> columnNames = new ArrayList<>();
			for (Cell cellColumnHeadings : worksheet.getRow(0)) {
				System.out.println(cellColumnHeadings.getStringCellValue() + "##");
				columnNames.add(cellColumnHeadings.getStringCellValue());
			}

			worksheet.removeRow(worksheet.getRow(0));

			for (Row row : worksheet) {
				Map<String, Object> map = new HashMap<>();

				if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(0).getStringCellValue() + "--");
					String companyName = row.getCell(0).getStringCellValue();
					map.put(Constants.COMPANY_NAME, companyName);
				}

				if (row.getCell(1).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(1).getStringCellValue() + "--");
					String website = row.getCell(1).getStringCellValue();
					map.put(Constants.WEBSITE, website);
					String logoUrl = null;
					logoUrl = "http://logo.clearbit.com/";
					if (website.contains("/")) {
						// logo.clearbit.com/spotify.com
						logoUrl = logoUrl + website.substring(0, website.lastIndexOf('/'));
					} else {
						logoUrl = logoUrl + website;
					}

					System.out.println("##############Logo URL############# --" + logoUrl);
					map.put(Constants.LOGO, logoUrl);

				}

				if (row.getCell(2).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(2).getStringCellValue() + "--");
					String country = row.getCell(2).getStringCellValue();
					map.put(Constants.COUNTRY, country);
				}

				if (row.getCell(3).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(3).getStringCellValue() + "--");
					String categoryName = row.getCell(3).getStringCellValue();
					String categoryId = fetchCategoryIdByCategoryName(categoryName, allCategories);
					List<String> categoryIds = new ArrayList<>();		
					categoryIds.add(categoryId);
					map.put("company_categories", categoryIds);
				}
				
				List<String> industryTags = new ArrayList<>();
				
				if (row.getCell(4) == null) {
					map.put("industry_tag_1", "");
				} else {
					if (row.getCell(4).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(4).getStringCellValue() + "--");
						String industryTag1 = row.getCell(4).getStringCellValue();
						industryTags.add(industryTag1);
						map.put("industry_tag_1", industryTag1);
					}
				}

				if (row.getCell(5) == null) {
					map.put("industry_tag_2", "");
				} else {
					if (row.getCell(5).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(5).getStringCellValue() + "--");
						String industryTag2 = row.getCell(5).getStringCellValue();
						industryTags.add(industryTag2);
						map.put("industry_tag_2", industryTag2);
					}
				}

				if (row.getCell(6) == null) {
					map.put("industry_tag_3", "");
				} else {
					if (row.getCell(6).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(6).getStringCellValue() + "--");
						String industryTag3 = row.getCell(6).getStringCellValue();
						industryTags.add(industryTag3);
						map.put("industry_tag_3", industryTag3);
					}
				}

				if (row.getCell(7) == null) {
					map.put("industry_tag_4", "");
				} else {
					if (row.getCell(7).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(7).getStringCellValue() + "--");
						String industryTag4 = row.getCell(7).getStringCellValue();
						industryTags.add(industryTag4);
						map.put("industry_tag_4", industryTag4);
					}
				}

				map.put("industry_tags", industryTags);
				
				if (row.getCell(8) == null) {
					map.put("gender", "");
				} else {
					if (row.getCell(8).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(8).getStringCellValue() + "--");
						String gender = row.getCell(8).getStringCellValue();
						map.put("gender", gender);
					}
				}

				if (row.getCell(9) == null) {
					map.put("targeted_gender", "");
				} else {
					if (row.getCell(9).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(9).getStringCellValue() + "--");
						String targetGender = row.getCell(9).getStringCellValue();
						map.put("targeted_gender", targetGender);
					}
				}

				if (row.getCell(10) == null) {
					map.put("age_range", "");
				} else {
					if (row.getCell(10).getCellTypeEnum() == CellType.STRING) {
						System.out.print(row.getCell(10).getStringCellValue() + "--");
						String ageRange = row.getCell(10).getStringCellValue();
						map.put("age_range", ageRange);
					}
				}

				companies.add(map);
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return companies;
	}

	public List<Map<String, Object>> uploadCategories(UploadExcelDTO dto) {
		List<Map<String, Object>> categories = new ArrayList<>();
		try {

			Workbook offices;

			if (dto.getExcelFile().getOriginalFilename().endsWith(".xlsx")) {
				offices = new XSSFWorkbook(dto.getExcelFile().getInputStream());
			} else {
				offices = new HSSFWorkbook(dto.getExcelFile().getInputStream());
			}

			Sheet worksheet = offices.getSheetAt(0);
			List<String> columnNames = new ArrayList<>();
			for (Cell cellColumnHeadings : worksheet.getRow(0)) {
				System.out.println(cellColumnHeadings.getStringCellValue() + "##");
				columnNames.add(cellColumnHeadings.getStringCellValue());
			}

			worksheet.removeRow(worksheet.getRow(0));

			for (Row row : worksheet) {
				Map<String, Object> map = new HashMap<>();

				if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(0).getStringCellValue() + "--");
					String category = row.getCell(0).getStringCellValue();
					map.put("category_name", category);

				}

				if (row.getCell(1).getCellTypeEnum() == CellType.STRING) {
					System.out.print(row.getCell(1).getStringCellValue() + "--");
					String influencesString = row.getCell(1).getStringCellValue();
					List<String> influences = new ArrayList<String>(Arrays.asList(influencesString.split(",")));
					List<String> influenceIds = new ArrayList<>();
					for (String influence : influences) {
						String influenceId = getInfluenceIdByName(influence.trim());
						influenceIds.add(influenceId);
					}
					map.put("influence_ids", influenceIds);
				}
				map.put("description", "");
				categories.add(map);
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return categories;

	}

	private String getInfluenceIdByName(String influenceName) {
		InfluenceClient influenceClient = new InfluenceClient();
		List<Map<String, String>> influences = influenceClient.fetchInfluencesClient(null,
				"influence/fetchAllInfluences");
		String influenceId = null;

		for (Map<String, String> influenceRow : influences) {

			if (influenceName.equalsIgnoreCase(influenceRow.get("influence"))) {
				influenceId = influenceRow.get("id");
				return influenceId;
			}

		}

		return null;
	}

	public String fetchCategoryIdByCategoryName(String categoryName, List<Map<String, Object>> allCategories) {
		return (String) allCategories.stream()
				.filter(c -> categoryName.equalsIgnoreCase(c.get(Constants.CATEGORY).toString()))
				.map(c -> c.get(Constants.ID)).collect(CustomCollector.singletonCollector());
	}

}
