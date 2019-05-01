package com.finra.exercise.finra_coding;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finra.exercise.finra_coding.data.FileManager;
import com.finra.exercise.finra_coding.data.FinraCodingFileDAO;
import com.finra.exercise.finra_coding.data.FinraCodingFileMetadata;
import com.finra.exercise.finra_coding.error.MetaDataException;

/**
 * Upload/Download for file stream
 * 
 * @author Dan Little
 *
 */
@RestController
public class FinraCodingController {
	
	Logger logger = LoggerFactory.getLogger(FinraCodingController.class);
	
	@Autowired
	private FinraCodingFileDAO fileDAO;
	
	@Autowired
	private FileManager fileManager;
	
	/**
	 * Upload interface that takes a multipart form upload file and stores it locally as well as 
	 * metadata in a database.
	 * 
	 * @param file 				MultipartFile upload which will be stored locally. Must be unique filename to the owner.
	 * 
	 * @param metaOwner			Owner name to store the file for, max 255 characters
	 * @param metaDescription	Description of the file, max 2048 characters
	 * 
	 * @return  	Json response for success in jSend standard format
	 * 
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws MetaDataException 
	 */
	@PostMapping(value = "/upload", consumes = { "multipart/form-data" }, produces = { "application/json" })
	public FinraCodingFileMetadata uploadFile(@RequestParam("file") MultipartFile file,  @RequestParam("owner") String metaOwner, @RequestParam("description") String metaDescription) throws IllegalStateException, IOException, MetaDataException{
		logger.info("Upload request recieved by "+metaOwner+" for "+file.getOriginalFilename());
		
		//Store file locally inside of directory with owner's name to keep files unique across owners
		String filePath = fileManager.recieveFile(file, metaOwner);
		logger.debug("Uploaded "+file.getOriginalFilename() + " to "+filePath);
		
		//Push to database if successful
		FinraCodingFileMetadata meta = null;
		try {
			logger.debug("Saving file metadata to database");
			meta = new FinraCodingFileMetadata(file.getOriginalFilename(), filePath, metaOwner, metaDescription);
			fileDAO.save(meta);
		} catch (Exception e) {
			throw new MetaDataException(e.getLocalizedMessage(), e, meta);
		}
		
		//Complete
		logger.info("Upload request successful for "+metaOwner+"'s file "+file.getOriginalFilename());
		logger.debug(meta.toString());
		return meta;
	}
	
	/**
	 * Retrieve file metadata for requested fileId in json format
	 * 
	 * @param fileId 	file id to retrieve metadata for
	 * 
	 * @return 	Json formatted metadata inlcluding
	 * 
	 * 			id
	 * 			filename
	 * 			owner
	 * 			description
	 * 
	 */
	@GetMapping(value = "/fileMeta/{fileId}")
	@ResponseBody
	public FinraCodingFileMetadata getMetadata(@PathVariable Long fileId){
		
		Optional<FinraCodingFileMetadata> metaSearch = fileDAO.findById(fileId);
		if(!metaSearch.isPresent()){
			throw new IllegalArgumentException("Cannot find file by id "+fileId);
		}
		
		FinraCodingFileMetadata meta = metaSearch.get();
		
		return meta;
	}
	
	/**
	 * Return byte array contents of 
	 * @param fileId
	 * @return
	 * @throws MetaDataException
	 */
	@GetMapping(value = "/fileContent/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] getContents(@PathVariable Long fileId) throws JSONException, MetaDataException{
		
		Optional<FinraCodingFileMetadata> metaSearch = fileDAO.findById(fileId);
		if(!metaSearch.isPresent()){
			throw new IllegalArgumentException("Cannot find file by id "+fileId);
		}
		
		//Get file for reading
		FinraCodingFileMetadata meta = metaSearch.get();
		File localFile = new File(fileManager.getAbsolutePath(meta.getRelativefilepath()));
		FileInputStream fis = null;
		
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) localFile.length()];
        try{
            fis = new FileInputStream(localFile);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            throw new MetaDataException("Error while reading file "+meta.getRelativefilepath(), ioExp, meta);
        }
        
		return bArray;
	}
	
	@GetMapping(value = "/owners/{owner}")
    public Long[] getOwnerFiles(@PathVariable String owner) throws JSONException{
		
		List<FinraCodingFileMetadata> metaSearch = fileDAO.findByOwnerContainingIgnoreCase(owner);
		if(metaSearch.isEmpty()){
			throw new IllegalArgumentException("Cannot find files for owner "+owner);
		}
		
		Long[] fileList = new Long[metaSearch.size()];
		for(int pos = 0; pos<metaSearch.size(); pos++){
			fileList[pos] = metaSearch.get(pos).getId();
		}
		
		return fileList;
	}
}
