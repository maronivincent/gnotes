package com.saintsau.slam2.gnotes3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.Matiere;
import com.saintsau.slam2.gnotes3.dto.EtudiantDTO;
import com.saintsau.slam2.gnotes3.dto.MatiereDTO;
import com.saintsau.slam2.gnotes3.service.EtudiantService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService; // Assume a service layer for better separation of concerns.

    // GET /etudiants/
    @GetMapping
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantService.getAllEtudiantDtos();
    }

    // GET /etudiants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiantById(@PathVariable int id) {
        Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantDtoById(id);
        return etudiant.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST /etudiants/
    @PostMapping
    public Etudiant createEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.saveEtudiant(etudiant);
    }

    // PUT /etudiants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable int id, @RequestBody Etudiant updatedEtudiant) {
        Optional<Etudiant> existingEtudiant = etudiantService.getEtudiantById(id);
        if (existingEtudiant.isPresent()) {
            updatedEtudiant.setNumero(id);
            return ResponseEntity.ok(etudiantService.saveEtudiant(updatedEtudiant));
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /etudiants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable int id) {
        if (etudiantService.deleteEtudiantById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
   

    // GET /etudiants/{id}/matieres
    @GetMapping("/{id}/matieres")
    public ResponseEntity<List<MatiereDTO>> getMatieresByEtudiantId(@PathVariable int id) {
        List<MatiereDTO> matieres = etudiantService.getMatieresByEtudiantId(id);
        if (matieres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matieres);
    }

    // POST /etudiants/{id}/matieres
    @PostMapping("/{id}/matieres")
    public ResponseEntity<Etudiant> addMatiereToEtudiant(@PathVariable int id, @Validated @RequestBody Matiere matiere) {
        Optional<Etudiant> etudiant = etudiantService.getEtudiantById(id);
        if (etudiant.isPresent()) {
            // Ensure the Matiere is associated with the student
            matiere.setEtudiant(etudiant.get());
            
            // Add the Matiere to the student's list of matieres
            etudiant.get().getMatieres().add(matiere);
            
            // Save the updated student and return a response
            Etudiant updatedEtudiant = etudiantService.saveEtudiant(etudiant.get());
            return ResponseEntity.ok(updatedEtudiant);
        }
        // If the student is not found, return 404
        return ResponseEntity.notFound().build();
    }

    

    // GET /etudiants/{id}/matieres/{matiereId}
    @GetMapping("/{id}/matieres/{matiereId}")
    public ResponseEntity<MatiereDTO> getMatiereByEtudiantIdAndMatiereId(
            @PathVariable int id,
            @PathVariable int matiereId) {
        return etudiantService.getMatiereByEtudiantIdAndMatiereId(id, matiereId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /etudiants/{id}/matieres/{matiereId}
    @PutMapping("/{id}/matieres/{matiereId}")
    public ResponseEntity<Matiere> updateMatiere(@PathVariable int id, @PathVariable int matiereId, @RequestBody Matiere updatedMatiere) {
        Optional<Etudiant> etudiant = etudiantService.getEtudiantById(id);
        if (etudiant.isPresent()) {
            Set<Matiere> matieres = etudiant.get().getMatieres();
            for (Matiere matiere : matieres) {
                if (matiere.getId() == matiereId) {
                    matiere.setIntitule(updatedMatiere.getIntitule());
                    matiere.setCoef(updatedMatiere.getCoef());
                    matiere.setNote(updatedMatiere.getNote());
                    etudiantService.saveEtudiant(etudiant.get());
                    return ResponseEntity.ok(matiere);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
    // DELETE /etudiants/{id}/matieres/{matiereId}
    @DeleteMapping("/{id}/matieres/{matiereId}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable int id, @PathVariable int matiereId) {
        Optional<Etudiant> etudiant = etudiantService.getEtudiantById(id);
        if (etudiant.isPresent()) {
            Set<Matiere> matieres = etudiant.get().getMatieres();
            boolean removed = matieres.removeIf(matiere -> matiere.getId() == matiereId);
            if (removed) {
                etudiantService.saveEtudiant(etudiant.get());
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
