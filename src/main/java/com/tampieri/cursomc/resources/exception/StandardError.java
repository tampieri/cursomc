package com.tampieri.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long timeStamp;
	private Integer Status;
	private String error;
	private String message;
	private String path;

	public StandardError(Long timeStamp, Integer status, String error, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		Status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}