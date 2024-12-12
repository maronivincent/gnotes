package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.jpaRepository.MatiereRepository;
import com.saintsau.slam2.gnotes30.service.EtudiantService;




@RestController
public class EtudiantController {
    private final EtudiantService etudiantService;
    private final MatiereRepository matiereRepository;

    public EtudiantController(EtudiantService etudiantService, MatiereRepository matiereRepository) {
    	super();
        this.etudiantService = etudiantService;
        this.matiereRepository = matiereRepository;
    }

    // POST: Add a new Etudiant
    @PostMapping("/etudiants")
    EntityModel<Etudiant> newEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);

        return EntityModel.of(savedEtudiant,
                linkTo(methodOn(EtudiantController.class).one(savedEtudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }

    @GetMapping("/etudiants")
    CollectionModel<EntityModel<Etudiant>> all() {
        List<EntityModel<Etudiant>> etudiants = StreamSupport.stream(etudiantService.findAllEtudiant().spliterator(), false)
                .map((Etudiant etudiant) -> EntityModel.of(etudiant,
                        linkTo(methodOn(EtudiantController.class).one(etudiant.getNumero())).withSelfRel()))    
                .collect(Collectors.toList());

        return CollectionModel.of(etudiants, 
                linkTo(methodOn(EtudiantController.class).all()).withSelfRel());
    }



    // GET: Single Etudiant
    @GetMapping("/etudiants/{id}")
    EntityModel<Etudiant> one(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.findEtudiantById(id);

        return EntityModel.of(etudiant,
                linkTo(methodOn(EtudiantController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }
}

//    // PUT: Update Etudiant
//    @PutMapping("/etudiants/{id}")
//    EntityModel<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant updatedEtudiant) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        etudiant.setNom(updatedEtudiant.getNom());
//        // Update other fields as needed
//        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);
//
//        return EntityModel.of(savedEtudiant,
//                linkTo(methodOn(EtudiantController.class).one(savedEtudiant.getNumero())).withSelfRel(),
//                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
//    }
//
//    // DELETE: Remove Etudiant
//    @DeleteMapping("/etudiants/{id}")
//    EntityModel<String> deleteEtudiant(@PathVariable Long id) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        // Delete associated Matieres
//        for (Matiere matiere : etudiant.getMatieres()) {
//            matiereRepository.delete(matiere);
//        }
//
//        etudiantService.deleteById(id);
//
//        return EntityModel.of("Etudiant supprimé",
//                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
//    }
//
//    // POST: Add Matieres to Etudiant
//    @PostMapping("/etudiants/{id}/matieres")
//    EntityModel<Etudiant> newMatierePerEtudiant(@PathVariable Long id, @RequestBody Set<Matiere> matieres) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        for (Matiere matiere : matieres) {
//            matiere.setEtudiant(etudiant);
//        }
//
//        etudiant.getMatieres().addAll(matieres);
//        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);
//
//        return EntityModel.of(savedEtudiant,
//                linkTo(methodOn(EtudiantController.class).one(savedEtudiant.getNumero())).withSelfRel(),
//                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
//    }
//    
//    @GetMapping("/etudiants/{id}/matieres")
//    CollectionModel<EntityModel<Matiere>> getMatieresByEtudiant(@PathVariable Long id) {
//        // Retrieve the Etudiant by ID, throw exception if not found
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        // Retrieve all Matieres for the Etudiant
//        List<EntityModel<Matiere>> matieres = etudiant.getMatieres().stream()
//                .map((Matiere matiere) -> EntityModel.of(matiere,
//                        linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, matiere.getId())).withSelfRel()))
//                .collect(Collectors.toList());
//
//        // Return the collection of matieres with HATEOAS links
//        return CollectionModel.of(matieres,
//                linkTo(methodOn(EtudiantController.class).getMatieresByEtudiant(id)).withSelfRel());
//    }
//
//    // GET: Single Matiere for Etudiant
//    @GetMapping("/etudiants/{id}/matieres/{idMatiere}")
//    EntityModel<Matiere> oneWithMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        Matiere matiere = etudiant.getMatieres().stream()
//                .filter(m -> m.getId().equals(idMatiere))
//                .findFirst()
//                .orElseThrow(() -> new MatiereNotFoundException(idMatiere));
//
//        return EntityModel.of(matiere,
//                linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, idMatiere)).withSelfRel(),
//                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
//    }
//
//    // PUT: Update Matiere
//    @PutMapping("/etudiants/{id}/matieres/{idMatiere}")
//    EntityModel<Matiere> updateMatiere(@PathVariable Long id, @PathVariable Long idMatiere, @RequestBody Matiere updatedMatiere) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        Matiere matiere = etudiant.getMatieres().stream()
//                .filter(m -> m.getId().equals(idMatiere))
//                .findFirst()
//                .orElseThrow(() -> new MatiereNotFoundException(idMatiere));
//
//        matiere.setIntitule(updatedMatiere.getIntitule());
//        matiere.setType(updatedMatiere.getType());
//        matiere.setCoef(updatedMatiere.getCoef());
//        matiere.setNote(updatedMatiere.getNote());
//
//        Matiere savedMatiere = matiereRepository.save(matiere);
//
//        return EntityModel.of(savedMatiere,
//                linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, idMatiere)).withSelfRel(),
//                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
//    }
//
//    // DELETE: Remove Matiere
//    @DeleteMapping("/etudiants/{id}/matieres/{idMatiere}")
//    EntityModel<String> deleteMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
//        Etudiant etudiant = etudiantService.findEtudiantById(id)
//                .orElseThrow(() -> new EtudiantNotFoundException(id));
//
//        Matiere matiere = etudiant.getMatieres().stream()
//                .filter(m -> m.getId().equals(idMatiere))
//                .findFirst()
//                .orElseThrow(() -> new MatiereNotFoundException(idMatiere));
//
//        etudiant.getMatieres().remove(matiere);
//        matiereRepository.delete(matiere);
//        etudiantService.saveEtudiant(etudiant);
//
//        return EntityModel.of("Matière supprimé",
//                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
//    }




