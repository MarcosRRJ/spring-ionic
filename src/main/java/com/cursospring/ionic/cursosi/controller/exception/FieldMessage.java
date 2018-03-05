package com.cursospring.ionic.cursosi.controller.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

	public FieldMessage() {

	}
	public FieldMessage(String fieldName, String msg) {
		this.fieldName = fieldName;
		this.message = msg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}

}
