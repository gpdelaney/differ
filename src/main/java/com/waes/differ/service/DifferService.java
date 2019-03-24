package com.waes.differ.service;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;

public interface DifferService {

	public void saveJson(EncodedJsonDAO encodedJson);

	public void saveLeftJson(String id, byte[] encodedJson);

	public void saveRightJson(String id, byte[] encodedJson);

	String compareJson(String id);

	public String compareJsonInDetail(EncodedJsonDAO encodedJsonLeft, EncodedJsonDAO encodedJsonRight);

	EncodedJsonDAO getJson(EncodedJsonIdentity encodedJson);

}
