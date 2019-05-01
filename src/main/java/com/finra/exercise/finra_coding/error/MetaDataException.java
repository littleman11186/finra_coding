package com.finra.exercise.finra_coding.error;

import com.finra.exercise.finra_coding.data.FileMetadata;

public class MetaDataException extends Exception {
	private FileMetadata meta;
	
	public MetaDataException(String message, Throwable ex, FileMetadata meta){
		super(message, ex);
		this.meta = meta;
	}

	public FileMetadata getMeta() {
		return meta;
	}

	public void setMeta(FileMetadata meta) {
		this.meta = meta;
	}
	
	
}
