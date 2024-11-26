package com.saintsau.slam2.gnotes3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.dto.EtudiantDTO;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
	
	 @Query("SELECT new com.saintsau.slam2.gnotes3.dto.EtudiantDTO(e.numero, e.nom) FROM Etudiant e")
	    List<EtudiantDTO> findAllEtudiantDtos();
	 
	 @Query("SELECT new com.saintsau.slam2.gnotes3.dto.EtudiantDTO(e.numero, e.nom) FROM Etudiant e WHERE e.numero = :id")
	 Optional<EtudiantDTO> findEtudiantDtoById(@Param("id") int id);

}
