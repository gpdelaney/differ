package com.waes.differ.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waes.differ.model.EncodedJsonDTO;
import com.waes.differ.model.EncodedJsonIdentity;

@Transactional
public interface EncodedJsonRepository extends JpaRepository<EncodedJsonDTO, EncodedJsonIdentity>{

}
