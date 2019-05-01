package com.finra.exercise.finra_coding.error;

import com.finra.exercise.finra_coding.data.FinraCodingFileMetadata;

public class MetaDataException extends Exception {
	private FinraCodingFileMetadata meta;
	
	public MetaDataException(String message, Throwable ex, FinraCodingFileMetadata meta){
		super(message, ex);
		this.meta = meta;
	}

	public FinraCodingFileMetadata getMeta() {
		return meta;
	}

	public void setMeta(FinraCodingFileMetadata meta) {
		this.meta = meta;
	}
	
	
}
