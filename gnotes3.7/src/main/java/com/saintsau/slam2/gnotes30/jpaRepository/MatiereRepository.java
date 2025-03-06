package com.saintsau.slam2.gnotes30.jpaRepository;

import org.springframework.data.repository.CrudRepository;

import com.saintsau.slam2.gnotes30.entity.Matiere;

public interface MatiereRepository extends CrudRepository<Matiere, Long> {
	Matiere findById(long id);
}
