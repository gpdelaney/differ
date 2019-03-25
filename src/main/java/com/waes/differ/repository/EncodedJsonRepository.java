package com.waes.differ.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waes.differ.model.EncodedJsonDAO;
import com.waes.differ.model.EncodedJsonIdentity;

@Transactional
public interface EncodedJsonRepository extends JpaRepository<EncodedJsonDAO, EncodedJsonIdentity>{
	
	Optional<EncodedJsonDAO> findById(EncodedJsonIdentity id);

}
