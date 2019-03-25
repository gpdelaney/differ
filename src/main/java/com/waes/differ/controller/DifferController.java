package com.waes.differ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waes.differ.model.EncodedJsonResponseDTO;
import com.waes.differ.service.DifferService;

/**
 * Main class controller for the Differ App.
 * 
 * @author will
 *
 */
@RestController
class DifferController {

	@Autowired
	private DifferService differService;

	/**
	 * Saves and encodes the encoded left json.
	 * 
	 * @param id            String - ID of the encoded json to be saved (if existing
	 *                      it replaces the previous).
	 * @param unencodedJson String - uncoded json.
	 */
	@PutMapping(path = "/v1/diff/json/{id}/left")
	@ResponseStatus(value = HttpStatus.OK)
	public void receivePlainJsonLeft(@PathVariable(value = "id") String id, @RequestBody String unencodedJson) {
		differService.saveLeftUnencodedJson(id, unencodedJson);
	}

	/**
	 * Saves and encodes the encoded right json.
	 * 
	 * @param id            String - ID of the encoded json to be saved (if existing
	 *                      it replaces the previous).
	 * @param unencodedJson String - String with the uncoded json.
	 */
	@PutMapping(path = "/v1/diff/json/{id}/right")
	@ResponseStatus(value = HttpStatus.OK)
	public void receivePlainJsonRight(@PathVariable(value = "id") String id, @RequestBody String unencodedJson) {
		differService.saveRightUnencodedJson(id, unencodedJson);
	}

	/**
	 * Saves the encoded left json.
	 * 
	 * @param id          String - ID of the encoded json to be saved (if existing
	 *                    it replaces the previous).
	 * @param encodedJson byte[] - Array of bytes with the encoded json.
	 */
	@PutMapping(path = "/v1/diff/{id}/left")
	@ResponseStatus(value = HttpStatus.OK)
	public void receiveLeftEncodedJson(@PathVariable(value = "id") String id, @RequestBody byte[] encodedJson) {
		differService.saveLeftJson(id, encodedJson);
	}

	/**
	 * Saves the encoded right json.
	 * 
	 * @param id          String - ID of the encoded json to be saved (if existing
	 *                    it replaces the previous).
	 * @param encodedJson byte[] - Array of bytes with the encoded json.
	 */
	@PutMapping(path = "/v1/diff/{id}/right")
	@ResponseStatus(value = HttpStatus.OK)
	public void receiveRightEncodedJson(@PathVariable(value = "id") String id, @RequestBody byte[] encodedJson) {
		differService.saveRightJson(id, encodedJson);
	}

	/**
	 * Returns the result of compared Json by Id. If no json found with those ID's
	 * it will return a 404-Not Found.
	 * 
	 * @param comparedJson String - Id of the Json previously inserted to compare.
	 * @return {@link EncodedJsonResponseDTO} - Will contain the result of the
	 *         comparison and a Status Code 404 if not found.
	 */
	@GetMapping(path = "/v1/diff/{id}")
	public ResponseEntity<EncodedJsonResponseDTO> getJsonComparison(@PathVariable(value = "id") String comparedJson) {
		EncodedJsonResponseDTO comparisonResult = differService.compareJson(comparedJson);
		HttpStatus status = comparisonResult != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<EncodedJsonResponseDTO>(comparisonResult, status);
	}
	
	/**
	 * Utility method to directly decode jsons
	 * @param encodedJson {@link EncodedJsonResponseDTO} 
	 * @return ResponseEntity - With decoded json and httpstatus
	 * 
	 */
	@GetMapping(path ="/v1/diff/decode/{encodedJson}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<EncodedJsonResponseDTO> decodeJson(@PathVariable(value = "encodedJson") byte[] encodedJson) {
		EncodedJsonResponseDTO decodedJson = differService.decodeJson(encodedJson);
		HttpStatus status = decodedJson != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<EncodedJsonResponseDTO>(decodedJson, status);
	}
	
	/**
	 * Utility method to directly encode jsons
	 * @param encodedJson {@link EncodedJsonResponseDTO} 
	 * @return ResponseEntity - With encoded json and httpstatus
	 * 
	 */
	@GetMapping(path ="/v1/diff/encode/{plainJson}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<EncodedJsonResponseDTO> encodeJson(@PathVariable(value="plainJson") String jsonToEncode) {
		EncodedJsonResponseDTO decodedJson = new EncodedJsonResponseDTO(new String(differService.encodeJson(jsonToEncode)));
		HttpStatus status = decodedJson != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<EncodedJsonResponseDTO>(decodedJson, status);
		
	}
}
