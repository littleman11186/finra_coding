package com.finra.exercise.finra_coding;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinraCodingApplicationTests {

	@Autowired
	private FinraCodingController controller;
	
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
