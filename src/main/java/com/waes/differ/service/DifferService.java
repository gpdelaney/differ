package com.waes.differ.service;

import com.waes.differ.model.EncodedJsonDTO;

public interface DifferService {
	
	public void saveJson(EncodedJsonDTO encodedJson);
	
	public void saveLeftJson(String id, byte[] encodedJson);
	
	public void saveRightJson(String id, byte[] encodedJson);

	String compareJson(String id);

}
