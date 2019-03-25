package com.waes.differ.model;

/**
 * DTO with the json comparison result.
 * 
 * @author will
 */
public class EncodedJsonResponseDTO {
	
	public EncodedJsonResponseDTO(String jsonOperationResult) {
		super();
		this.jsonOperationResult = jsonOperationResult;
	}
	
	public EncodedJsonResponseDTO() {
		super();
	}

	private String jsonOperationResult;

	public String getJsonOperationResult() {
		return jsonOperationResult;
	}

	public void setJsonOperationResult(String jsonOperationResult) {
		this.jsonOperationResult = jsonOperationResult;
	}

}
