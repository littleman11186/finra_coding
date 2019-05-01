package com.finra.exercise.finra_coding.messaging;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finra.exercise.finra_coding.data.FinraCodingFileMetadata;

public class SuccessResponseJson extends ResponseJson {
	public SuccessResponseJson(){
		super("success");
	}
	public SuccessResponseJson(Map<String, Object> data){
		super("success", data);
	}
}
