package com.waes.differ.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.differ.model.EncodedJsonDTO;
import com.waes.differ.model.EncodedJsonIdentity;
import com.waes.differ.model.JsonPosition;
import com.waes.differ.repository.EncodedJsonRepository;

@Service
public class DifferServiceImpl implements DifferService {
	@Autowired
	private EncodedJsonRepository jsonRepository;

	public void saveLeftJson(String id, byte[] encodedJson) {
		saveJson(new EncodedJsonDTO(new EncodedJsonIdentity(id, JsonPosition.LEFT), encodedJson));
	}

	@Override
	public void saveRightJson(String id, byte[] encodedJson) {
		saveJson(new EncodedJsonDTO(new EncodedJsonIdentity(id, JsonPosition.RIGHT), encodedJson));
	}

	@Override
	public void saveJson(EncodedJsonDTO encodedJson) {
		jsonRepository.save(encodedJson);
	}

	@Override
	public String compareJson(String id) {
		EncodedJsonDTO encodedJsonLeft = jsonRepository.getOne(new EncodedJsonIdentity(id, JsonPosition.LEFT));
		EncodedJsonDTO encodedJsonRight = jsonRepository.getOne(new EncodedJsonIdentity(id, JsonPosition.RIGHT));
		if (!Arrays.equals(encodedJsonLeft.getEncodedJson(), encodedJsonRight.getEncodedJson())) {
			if (encodedJsonLeft.getEncodedJson().length != encodedJsonRight.getEncodedJson().length) {
				return "different length";
			} else {
				byte[] leftArray = encodedJsonLeft.getEncodedJson();
				byte[] rightArray = encodedJsonRight.getEncodedJson();
				for (int i = 0; i < leftArray.length; i++) {
					if (leftArray[i] != rightArray[i]) {
						return "The different number is the last" + String.valueOf((rightArray.length - 1) - 1)
								+ " characters from the right array";
					}
				}
			}

		}
		return "the same";
	}

}
