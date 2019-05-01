package com.finra.exercise.finra_coding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.finra.exercise.finra_coding.data.FinraCodingFileDAO;
import com.finra.exercise.finra_coding.data.FinraCodingFileMetadata;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinraCodingApplicationTests {

	@Autowired
	private FinraCodingController controller;
	
	@Autowired 
	private FinraCodingFileDAO fileDAO;
	
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	
	@Test
	public void testDAORetrieveMeta() {
	    // given
	    FinraCodingFileMetadata file = new FinraCodingFileMetadata("filename.txt","Dan\\filename.txt", "Dan", "File description");
	    fileDAO.save(file);
	    fileDAO.flush();
	    
	    Optional<FinraCodingFileMetadata> found = fileDAO.findById(file.getId());
	 
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
	
	
}
