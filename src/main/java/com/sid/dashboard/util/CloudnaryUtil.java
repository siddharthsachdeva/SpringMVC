package com.sid.dashboard.util;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudnaryUtil {

	
	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			  "cloud_name", "siddharth",
			  "api_key", "***********",
			  "api_secret", "**********"));
	
	
		public String uploadImage(File file, String fileName, String folderName){
			
			Map<String, String> uploadResult = null;
			try {
				uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", "SidAdmin/"+folderName+"/"+fileName));
				cloudinary.url().imageTag("asda");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return uploadResult.get("secure_url");
		}
		
		public void deleteImage(String name, String folderName){
			
			Api api = cloudinary.api();
			try {
				api.deleteResources(Arrays.asList("BeezyAdmin/"+folderName+"/"+name), ObjectUtils.emptyMap());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

}
