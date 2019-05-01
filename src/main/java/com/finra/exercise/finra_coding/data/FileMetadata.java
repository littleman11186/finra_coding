package com.finra.exercise.finra_coding.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "metadata")
public class FileMetadata {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "filename", length = 255, nullable = false)
	private String filename;
	
	@JsonIgnore
	@Column(name = "relativefilepath", length = 1024, nullable = false)
	private String relativefilepath;
	
	@Column(name = "owner", length = 255, nullable = false)
	private String owner;
	
	@Column(name = "description", length = 2048, nullable = false)
	private String description;
	
	/**
	 * Constructor for an empty metadata
	 */
	public FileMetadata() {
		super();
	}
	
	/**
	 * Constructor for a new file metadata
	 * 
	 * @param filename
	 * @param filepath
	 * @param owner
	 * @param description
	 */
	public FileMetadata(String filename, String filepath, String owner, String description) {
		super();
		this.filename = filename;
		this.relativefilepath = filepath;
		this.owner = owner;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "FinraCodingFileMetadata [id=" + id + ", filename=" + filename + ", owner=" + owner + ", description=" + description + "]";
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRelativefilepath() {
		return relativefilepath;
	}

	public void setRelativefilepath(String relativefilepath) {
		this.relativefilepath = relativefilepath;
	}
	
}
