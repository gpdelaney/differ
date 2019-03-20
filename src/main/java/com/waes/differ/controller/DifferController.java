package com.waes.differ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waes.differ.service.DifferService;

@RestController
public class DifferController {
	
	@Autowired
	private DifferService differService;

	@RequestMapping(path="/v1/diff/{id}/left", method=RequestMethod.PUT)
	public void receiveLeftEncodedJson(@PathVariable(value = "id") String id, @RequestBody byte[] encodedJson) {
		differService.saveLeftJson(id, encodedJson);
	}
	@RequestMapping(path="/v1/diff/{id}/right", method=RequestMethod.PUT)
	public void receiveRightEncodedJson(@PathVariable(value = "id")String id, @RequestBody byte[] encodedJson) {
		differService.saveRightJson(id, encodedJson);
	}
	
	@RequestMapping(path="/v1/diff/{id}")
	public String getJsonComparison(@PathVariable(value ="id")String comparedJson) {
		return differService.compareJson(comparedJson);
	}
}
