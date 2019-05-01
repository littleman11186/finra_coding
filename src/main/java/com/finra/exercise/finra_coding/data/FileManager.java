package com.finra.exercise.finra_coding.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManager {
	
	@Value("${fileStoragePath}")
	protected String fileStoragePath;
	
	/**
	 * Converts a file input into a local file under a directory named by it's owner.
	 * Creates any missing directories
	 * 
	 * @param file MultipartFile
	 * 
	 * @return Relative path of the file to the root of the file storage
	 * 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String recieveFile(MultipartFile fileUpload, String owner) throws IllegalStateException, IOException, FileAlreadyExistsException{
		File fileStorage = new File(fileStoragePath);
		if(!fileStorage.exists()){
			fileStorage.mkdir();
		}
		
		String ownerPath = scrubForPath(owner);
		File ownerDir = new File(fileStoragePath + File.separator + ownerPath);
		if(!ownerDir.exists()){
			ownerDir.mkdir();
		}
		String filePath = fileStoragePath + File.separator + ownerPath + File.separator + fileUpload.getOriginalFilename();
		File file = new File(filePath);
		if (file.exists()){
			throw new FileAlreadyExistsException("File "+ fileUpload.getOriginalFilename() + " already exists for " + owner);
		}
		
		fileUpload.transferTo(file);
	    return ownerPath + File.separator + fileUpload.getOriginalFilename();
	}
	
	/**
	 * Returns the root of the file storage combined with the requested relative path 
	 * to form an absolute path
	 * 
	 * @param relativePath
	 * @return
	 */
	public String getAbsolutePath(String relativePath){
		return fileStoragePath + File.separator + relativePath;
	}
	
	/**
	 * Removes any non alpha/numeric/underscore to clean it for use in file pathing
	 * 
	 * @param owner
	 * @return clean string without non alpha/numeric/underscore
	 */
	public static String scrubForPath(String owner){
		return owner.replaceAll("\\W+", "");
	}
}
