package com.saintsau.slam2.gnotes3.service;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.dto.EtudiantDTO;
import com.saintsau.slam2.gnotes3.dto.MatiereDTO;
import com.saintsau.slam2.gnotes3.repository.EtudiantRepository;
import com.saintsau.slam2.gnotes3.repository.MatiereRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private MatiereRepository matiereRepository;
    
    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public List<EtudiantDTO> getAllEtudiantDtos() {
        // Using JPQL to fetch only numero and nom as DTOs
        return etudiantRepository.findAllEtudiantDtos();
    }

    @Override
    public Optional<Etudiant> getEtudiantById(int id) {
        return etudiantRepository.findById(id);
    }
    
    @Override
    public Optional<EtudiantDTO> getEtudiantDtoById(int id) {
        return etudiantRepository.findEtudiantDtoById(id);
    }

    @Override
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public boolean deleteEtudiantById(int id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<MatiereDTO> getMatieresByEtudiantId(int etudiantId) {
        return matiereRepository.findAllByEtudiantId(etudiantId);
    }

    @Override
    public Optional<MatiereDTO> getMatiereByEtudiantIdAndMatiereId(int etudiantId, int matiereId) {
        return matiereRepository.findByEtudiantIdAndMatiereId(etudiantId, matiereId);
    }
}
