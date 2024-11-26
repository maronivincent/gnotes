package com.saintsau.slam2.gnotes3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintsau.slam2.gnotes3.Matiere;
import com.saintsau.slam2.gnotes3.dto.MatiereDTO;
import com.saintsau.slam2.gnotes3.repository.MatiereRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MatiereServiceImpl implements MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    @Override
    public Optional<Matiere> getMatiereById(int id) {
        return matiereRepository.findById(id);
    }
    @Override
    public List<MatiereDTO> getMatieresByEtudiantId(int etudiantId) {
        return matiereRepository.findAllByEtudiantId(etudiantId);
    }

    @Override
    public Optional<MatiereDTO> getMatiereByEtudiantIdAndMatiereId(int etudiantId, int matiereId) {
        return matiereRepository.findByEtudiantIdAndMatiereId(etudiantId, matiereId);
    }

    @Override
    public Matiere saveMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    @Override
    public boolean deleteMatiere(int id) {
        if (matiereRepository.existsById(id)) {
            matiereRepository.deleteById(id);
            return true; // Deletion successful
        }
        return false; // Deletion failed, entity not found
    }
}
