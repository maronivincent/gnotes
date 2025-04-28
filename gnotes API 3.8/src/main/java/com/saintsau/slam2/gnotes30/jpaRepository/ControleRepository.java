package com.saintsau.slam2.gnotes30.jpaRepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saintsau.slam2.gnotes30.entity.Controle;

public interface ControleRepository extends CrudRepository<Controle, Long> {
	Controle findById(long id);
	
	
}
