package com.waes.differ.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DTO to persist the encoded json in the H2 database.
 * 
 * @author G. Delaney
 *
 */
@Entity
public class EncodedJsonDTO {

	public EncodedJsonDTO() {
		super();
	}

	public EncodedJsonDTO(EncodedJsonIdentity encodedJsonIdentity, byte[] encodedJson) {
		super();
		this.encodedJson = encodedJson;
		this.encodedJsonIdentity = encodedJsonIdentity;
	}
	@EmbeddedId
	private EncodedJsonIdentity encodedJsonIdentity;
	
	private byte[] encodedJson;

	public EncodedJsonIdentity getEncodedJsonIdentity() {
		return encodedJsonIdentity;
	}

	public void setEncodedJsonIdentity(EncodedJsonIdentity encodedJsonIdentity) {
		this.encodedJsonIdentity = encodedJsonIdentity;
	}

	public byte[] getEncodedJson() {
		return encodedJson;
	}

	public void setEncodedJson(byte[] encodedJson) {
		this.encodedJson = encodedJson;
	}

 
}
