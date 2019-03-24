package com.waes.differ.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;
import com.waes.differ.model.JsonPosition;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DifferServiceImplTest {
	
    @TestConfiguration
    static class DifferServiceImplTestContextConfiguration {
  
        @Bean
        public DifferService employeeService() {
            return new DifferServiceImpl();
        }
    }
	
	@Autowired
	private DifferService differService;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void whenValidId_thenLeftJsonShouldBeFound() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.LEFT),
				Base64.getEncoder().encode("something".getBytes())));
		EncodedJsonDAO encodedJsonDAO = differService.getJson(new EncodedJsonIdentity("1",JsonPosition.LEFT));
		assertThat(encodedJsonDAO.getEncodedJson()).isNotNull();
	}
	
	@Test
	public void whenValidId_thenRightJsonShouldBeFound() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.RIGHT),
				Base64.getEncoder().encode("something".getBytes())));
		EncodedJsonDAO encodedJsonDAO = differService.getJson(new EncodedJsonIdentity("1",JsonPosition.RIGHT));
		assertThat(encodedJsonDAO.getEncodedJson()).isNotNull();
	}
	
	@Test
	public void WhenValidIdAndPayload_thenLeftJsonShouldPersist() {
		differService.saveLeftJson("1",Base64.getEncoder().encode("something".getBytes()));
		EncodedJsonDAO encodedJsonDAO = entityManager.find(EncodedJsonDAO.class, new EncodedJsonIdentity("1", JsonPosition.LEFT));
		assertThat(encodedJsonDAO.getEncodedJson()).isNotNull();
	}
	
	@Test
	public void WhenValidIdAndPayload_thenRightJsonShouldPersist() {
		differService.saveRightJson("1",Base64.getEncoder().encode("something".getBytes()));
		EncodedJsonDAO encodedJsonDAO = entityManager.find(EncodedJsonDAO.class, new EncodedJsonIdentity("1", JsonPosition.RIGHT));
		assertThat(encodedJsonDAO.getEncodedJson()).isNotNull();
	}
	
	@Test
	public void whenTwoJsonPersisted_compareBothAndAreTheSame() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.RIGHT),
				Base64.getEncoder().encode("something".getBytes())));
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.LEFT),
				Base64.getEncoder().encode("something".getBytes())));
		String result = differService.compareJson("1");
		assertThat(result).isEqualTo("the same");
	}
	
	@Test
	public void whenTwoJsonPersisted_compareBothAndAreDifferentLength() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.RIGHT),
				Base64.getEncoder().encode("something".getBytes())));
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.LEFT),
				Base64.getEncoder().encode("same".getBytes())));
		String result = differService.compareJson("1");
		assertThat(result).isEqualTo("different length");
	}
	
	@Test
	public void whenTwoJsonPersisted_compareBothAndArSameLengthButDifferent() {
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.RIGHT),
				Base64.getEncoder().encode("something".getBytes())));
		entityManager.persist(new EncodedJsonDAO(new EncodedJsonIdentity("1", JsonPosition.LEFT),
				Base64.getEncoder().encode("sometheng".getBytes())));
		String result = differService.compareJson("1");
		assertThat(result).isEqualTo("The different number is the last10 characters from the right array");
	}
}
