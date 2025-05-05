package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.MatiereRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

    private final MatiereRepository matiereRepository;

    
     MatiereService(MatiereRepository matiereRepository) {
    	super();
        this.matiereRepository = matiereRepository;
    }

    
    public MatiereRepository getMatiereRepository() {
        return matiereRepository;
    }
    
    // GET: Retrieve all Matieres
    public List<Matiere> findAllMatiere() {
		
		return (List<Matiere>) matiereRepository.findAll(); 
	 }
    
    // GET: Retrieve a specific Matiere by ID
    public Matiere getMatiereById(Long id) {
       return matiereRepository.findById(id)
    		   .orElseThrow(() -> new MatiereNotFoundException(id));
    }

    // POST: Create a new Matiere
    public Matiere saveMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    // PUT: Update an existing Matiere
    public Matiere updateMatiere(Long id, Matiere updatedMatiere) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFoundException(id));

        matiere.setIntitule(updatedMatiere.getIntitule());
        // Add any other fields to update here
        return matiereRepository.save(matiere);
    }

    // DELETE: Delete a Matiere by ID
    public Matiere deleteMatiere(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFoundException(id));
        matiereRepository.delete(matiere);
        
        return matiere;
    }
}
