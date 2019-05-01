package com.finra.exercise.finra_coding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.finra.exercise.finra_coding.data.FileManager;
import com.finra.exercise.finra_coding.data.FileDAO;
import com.finra.exercise.finra_coding.data.FileMetadata;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinraCodingApplicationTests {

	@Autowired
	private FinraCodingController controller;
	
	@Autowired 
	private FileDAO fileDAO;
	
	@Autowired
	private FileManager fileManager;
	
	/**
	 * Quick test of the application loading
	 */
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	/**
	 * Quick test of the dao store and retrieval
	 */
	@Test
	public void testDAORetrieveMeta() {
	    // given
	    FileMetadata file = new FileMetadata("test_file.txt","Dan\\test_file.txt", "Dan", "File description");
	    fileDAO.save(file);
	    fileDAO.flush();
	    
	    Optional<FileMetadata> found = fileDAO.findById(file.getId());
	 
	    // then
	    assertTrue(found.isPresent());
	    assertThat(found.get().getFilename())
	      .isEqualTo(file.getFilename());
	    assertThat(found.get().getDescription())
	      .isEqualTo(file.getDescription());
	    assertThat(found.get().getOwner())
	      .isEqualTo(file.getOwner());
	    assertThat(found.get().getRelativefilepath())
	      .isEqualTo(file.getRelativefilepath());
	}
	
	/**
	 * Quick test of the file upload
	 */
	@Test
	public void testFileManagerUploadFile() throws FileAlreadyExistsException, IllegalStateException, IOException {
		MockMultipartFile mockFile = new MockMultipartFile("data", "test_file.txt", "text/plain", "some random text".getBytes());
		String path = fileManager.recieveFile(mockFile, "Dan");
		assertThat("Dan\\test_file.txt").isEqualTo(path);
	}
}
