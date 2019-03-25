package com.waes.differ.service;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;
import com.waes.differ.model.EncodedJsonResponseDTO;
import com.waes.differ.model.JsonPosition;
import com.waes.differ.repository.EncodedJsonRepository;

@Service
public class DifferServiceImpl implements DifferService {
	@Autowired
	private EncodedJsonRepository jsonRepository;

	public void saveLeftJson(String id, byte[] encodedJson) {
		saveJson(new EncodedJsonDAO(new EncodedJsonIdentity(id, JsonPosition.LEFT), encodedJson));
	}

	@Override
	public void saveRightJson(String id, byte[] encodedJson) {
		saveJson(new EncodedJsonDAO(new EncodedJsonIdentity(id, JsonPosition.RIGHT), encodedJson));
	}

	@Override
	public void saveJson(EncodedJsonDAO encodedJson) {
		jsonRepository.save(encodedJson);
	}

	@Override
	public EncodedJsonResponseDTO compareJson(String id) {
		EncodedJsonDAO encodedJsonLeft = getJson(new EncodedJsonIdentity(id, JsonPosition.LEFT));
		EncodedJsonDAO encodedJsonRight = getJson(new EncodedJsonIdentity(id, JsonPosition.RIGHT));
		if (encodedJsonLeft != null && encodedJsonRight != null) {
			return compareJsonInDetail(encodedJsonLeft, encodedJsonRight);
		}
		return null;
	}

	public EncodedJsonResponseDTO compareJsonInDetail(EncodedJsonDAO encodedJsonLeft, EncodedJsonDAO encodedJsonRight) {
		EncodedJsonResponseDTO jsonResult = new EncodedJsonResponseDTO();
		if (!Arrays.equals(encodedJsonLeft.getEncodedJson(), encodedJsonRight.getEncodedJson())) {
			if (encodedJsonLeft.getEncodedJson().length != encodedJsonRight.getEncodedJson().length) {
				jsonResult.setJsonOperationResult("Different Encoded Json Length");
			} else {
				byte[] leftArray = encodedJsonLeft.getEncodedJson();
				byte[] rightArray = encodedJsonRight.getEncodedJson();
				for (int i = 0; i < leftArray.length; i++) {
					if (leftArray[i] != rightArray[i]) {
						jsonResult.setJsonOperationResult("Different! The offset is the last "
								+ String.valueOf((rightArray.length - i) - 1) + " characters from the right array ");
					}
				}
			}
			return jsonResult;
		}
		jsonResult.setJsonOperationResult("the same");
		return jsonResult;
	}

	@Override
	public EncodedJsonDAO getJson(EncodedJsonIdentity encodedJson) {
		return jsonRepository.findById(encodedJson).orElse(null);
	}

	@Override
	public void saveLeftUnencodedJson(String id, String json) {
		byte[] encodedJson = encodeJson(json);
		saveJson(new EncodedJsonDAO(new EncodedJsonIdentity(id, JsonPosition.LEFT),encodedJson));
	}

	@Override
	public void saveRightUnencodedJson(String id, String json) {
		byte[] encodedJson = encodeJson(json);
		saveJson(new EncodedJsonDAO(new EncodedJsonIdentity(id, JsonPosition.RIGHT),encodedJson));	
		}

	@Override
	public String decodeJson(byte[] encodedJson) {
		return new String(Base64.getDecoder().decode(encodedJson));
	}

	@Override
	public byte[] encodeJson(String unencodedJson) {
		return Base64.getEncoder().encode(unencodedJson.getBytes());
	}
	
	

}
