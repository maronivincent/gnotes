package com.saintsau.slam2.gnotes3.service;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.dto.EtudiantDTO;
import com.saintsau.slam2.gnotes3.dto.MatiereDTO;

import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    List<Etudiant> getAllEtudiants();
    List<EtudiantDTO> getAllEtudiantDtos();
    Optional<EtudiantDTO> getEtudiantDtoById(int id); // New method for DTO
    Optional<Etudiant> getEtudiantById(int id);
    Etudiant saveEtudiant(Etudiant etudiant);
    boolean deleteEtudiantById(int id); // Updated to return a boolean
    List<MatiereDTO> getMatieresByEtudiantId(int etudiantId);
    Optional<MatiereDTO> getMatiereByEtudiantIdAndMatiereId(int etudiantId, int matiereId);
}
