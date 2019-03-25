package com.waes.differ.service;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;
import com.waes.differ.model.EncodedJsonResponseDTO;

/**
 * Manipulates the encoded Json by persisting it and recovering it for
 * comparison
 * 
 * @author will
 *
 */
public interface DifferService {

	/**
	 * Saves the Json into the DB.
	 * 
	 * @param encodedJson EncodedJsonDAO - The built DAO with the Json
	 */
	public void saveJson(EncodedJsonDAO encodedJson);

	/**
	 * Saves the left json
	 * 
	 * @param id          String - Id of the json to be saved (if exists it updates
	 *                    the record in the DB)
	 * @param encodedJson byte[] - Array of bytes with the encoded JSON
	 */
	public void saveLeftJson(String id, byte[] encodedJson);

	/**
	 * Saves the right Json
	 * 
	 * @param id          String - Id of the json to be saved (if exists it updates
	 *                    the record in the DB)
	 * @param encodedJson byte[] - Array of bytes with the encoded JSON
	 */
	public void saveRightJson(String id, byte[] encodedJson);

	/**
	 * Checks that the JSON entities are not null before doing the comparison
	 * 
	 * @param id String - ID of the JSON's to compare
	 * @return {@link EncodedJsonResponseDTO}
	 */
	public EncodedJsonResponseDTO compareJson(String id);

	/**
	 * Compares the JSON in detaul and return the result of said comparison (The
	 * Same, Different and the offset, different length)
	 * 
	 * @param encodedJsonLeft  {@link EncodedJsonDAO}
	 * @param encodedJsonRight {@link EncodedJsonDAO}
	 * @return {@link EncodedJsonResponseDTO}
	 */
	public EncodedJsonResponseDTO compareJsonInDetail(EncodedJsonDAO encodedJsonLeft, EncodedJsonDAO encodedJsonRight);

	/**
	 * Gets the encoded Json from the database with the composite key
	 * 
	 * @param encodedJson {@link EncodedJsonIdentity}
	 * @return {@link EncodedJsonDAO}
	 */
	public EncodedJsonDAO getJson(EncodedJsonIdentity encodedJson);

	/**
	 * Encodes the left JSon in Base64 and stores it.
	 * 
	 * @param id   String - Json id
	 * @param json String - Unencoded Json
	 */
	public void saveLeftUnencodedJson(String id, String json);

	/**
	 * Encodes the right JSon in Base64 and stores it.
	 * 
	 * @param id   String - Json id
	 * @param json String - Unencoded Json
	 */
	public void saveRightUnencodedJson(String id, String json);

	/**
	 * Decode the Base64 Json
	 * 
	 * @param encodedJson byte[] - byte array of encoded json
	 * @return {@link EncodedJsonResponseDTO} - The decoded Json
	 */
	public EncodedJsonResponseDTO decodeJson(byte[] encodedJson);

	/**
	 * Encode the Json in Base64
	 * 
	 * @param unencodedJson - String with the unencoded json
	 * @return byte[] - Byte array with the encoded json.
	 */
	public byte[] encodeJson(String unencodedJson);

}
