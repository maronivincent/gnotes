package com.saintsau.slam2.jpaRepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saintsau.slam2.entity.Etudiant;

public interface EtudiantRepository extends CrudRepository<Etudiant, Long> {
	Optional<Etudiant>  findById(Long id);
	
}
