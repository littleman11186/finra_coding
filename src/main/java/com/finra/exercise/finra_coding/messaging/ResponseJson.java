package com.finra.exercise.finra_coding.messaging;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to handle JSend standard for object response
 * 
 * @author Dan Little
 *
 */
public class ResponseJson {
	private String status;
	private Map<String, Object> data;
	
	public ResponseJson(String status){
		this.status = status;
	}
	
	public ResponseJson(String status, Map<String, Object> data){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void setData(Object genericData){
		Map<String, Object> dataMapped = null;
        if (data != null){
        	ObjectMapper oMapper = new ObjectMapper();
        	dataMapped = oMapper.convertValue(data, Map.class);
        	this.data = dataMapped;
        }
	}
	
	
}
