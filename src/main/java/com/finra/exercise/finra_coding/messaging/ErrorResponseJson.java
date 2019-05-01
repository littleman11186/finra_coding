package com.finra.exercise.finra_coding.messaging;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class to handle error status for JSend message standard
 * @author Dan Little
 *
 */
public class ErrorResponseJson extends ResponseJson {
	private String message;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
	
	public ErrorResponseJson(String message){
		super("failure");
		this.message = message;
		timestamp = LocalDateTime.now();
	}
	
	public ErrorResponseJson(String message, Map<String, Object> data){
		super("failure", data);
		this.message = message;
		timestamp = LocalDateTime.now();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
