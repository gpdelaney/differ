package com.waes.differ.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class EncodedJsonIdentity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	public EncodedJsonIdentity() {
		super();
	}

	public EncodedJsonIdentity(@NotNull String id, @NotNull JsonPosition jsonPosition) {
		super();
		this.id = id;
		this.jsonPosition = jsonPosition;
	}

	@NotNull
	private String id;
	
	@NotNull
	private JsonPosition jsonPosition;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JsonPosition getJsonPosition() {
		return jsonPosition;
	}

	public void setJsonPosition(JsonPosition jsonPosition) {
		this.jsonPosition = jsonPosition;
	}

}
