package com.saintsau.slam2.gnotes3.repository;

import com.saintsau.slam2.gnotes3.dto.MatiereDTO;
import com.saintsau.slam2.gnotes3.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {
    // Fetch all matieres for a specific etudiant
    @Query("SELECT new com.saintsau.slam2.gnotes3.dto.MatiereDTO(m.id, m.intitule, m.type, m.note, m.coef) " +
           "FROM Matiere m WHERE m.etudiant.numero = :etudiantId")
    List<MatiereDTO> findAllByEtudiantId(@Param("etudiantId") int etudiantId);

    // Fetch a specific matiere by ID for a specific etudiant
    @Query("SELECT new com.saintsau.slam2.gnotes3.dto.MatiereDTO(m.id, m.intitule, m.type, m.note, m.coef) " +
           "FROM Matiere m WHERE m.etudiant.numero = :etudiantId AND m.id = :matiereId")
    Optional<MatiereDTO> findByEtudiantIdAndMatiereId(@Param("etudiantId") int etudiantId, @Param("matiereId") int matiereId);
}
