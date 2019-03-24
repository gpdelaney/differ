package com.waes.differ.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;
import com.waes.differ.model.JsonPosition;
import com.waes.differ.repository.EncodedJsonRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EncodedJsonRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EncodedJsonRepository encodedJsonRepository;

	@Test
	public void persistingNewJsonLeft() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.LEFT),
				Base64.getEncoder().encode("something".getBytes())));
		EncodedJsonDAO json = encodedJsonRepository.getOne(new EncodedJsonIdentity("1", JsonPosition.LEFT));
		assertThat(json.getEncodedJsonIdentity().getId().equals("1"));
	}
	
	@Test
	public void persistingNewJsonRight() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.RIGHT),
				Base64.getEncoder().encode("something".getBytes())));
		EncodedJsonDAO json = encodedJsonRepository.getOne(new EncodedJsonIdentity("1", JsonPosition.RIGHT));
		assertThat(json.getEncodedJsonIdentity().getId().equals("1"));
	}

}
