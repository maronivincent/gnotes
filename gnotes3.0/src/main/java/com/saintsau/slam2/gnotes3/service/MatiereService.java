package com.saintsau.slam2.gnotes3.service;

import java.util.List;
import java.util.Optional;

import com.saintsau.slam2.gnotes3.Matiere;
import com.saintsau.slam2.gnotes3.dto.MatiereDTO;

public interface MatiereService {
	List<MatiereDTO> getMatieresByEtudiantId(int etudiantId);
    Optional<MatiereDTO> getMatiereByEtudiantIdAndMatiereId(int etudiantId, int matiereId);
    Optional<Matiere> getMatiereById(int id);
     Matiere saveMatiere(Matiere matiere);
    boolean deleteMatiere(int id); // Updated to return a boolean
}
