package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
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


    @PutMapping("/etudiants/{id}")
    public EntityModel<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant updatedEtudiant) {
        // Call service method to update Etudiant
        Etudiant etudiant = etudiantService.updateEtudiant(id, updatedEtudiant);
        
        // Return the updated Etudiant wrapped in EntityModel with HATEOAS links
        return EntityModel.of(etudiant,
                linkTo(methodOn(EtudiantController.class).one(etudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    } 


    @DeleteMapping("/etudiants/{id}")
    EntityModel<Map<String, Object>> deleteEtudiant(@PathVariable Long id) {
        // Find the Etudiant by ID or throw exception
        Etudiant deletedEtudiant = etudiantService.deleteById(id);

        // Create the response message
        String message = "Etudiant supprimé: " + deletedEtudiant.getNom();

        // Prepare the response body as a map
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("etudiant", deletedEtudiant);  // You can include other Etudiant details if needed

        // Return the response with HATEOAS links
        return EntityModel.of(response,
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }




    // POST: Add Matieres to Etudiant
    @PostMapping("/etudiants/{id}/matieres")
    public EntityModel<Etudiant> newMatierePerEtudiant(@PathVariable Long id, @RequestBody Set<Matiere> matieres) {
        // Call the service method to add Matieres to the Etudiant
        Etudiant updatedEtudiant = etudiantService.saveMatiereByEtudiant(id, matieres);

        // Return the updated Etudiant wrapped in EntityModel with HATEOAS links
        return EntityModel.of(updatedEtudiant,
                linkTo(methodOn(EtudiantController.class).one(updatedEtudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }
    
    @GetMapping("/etudiants/{id}/matieres")
    public CollectionModel<EntityModel<Matiere>> getMatieresByEtudiant(@PathVariable Long id) {
        // Use the service method to find all Matieres for the Etudiant
        Set<Matiere> matieres = etudiantService.findAllMatiereByEtudiant(id);

        // Map Matieres to EntityModels and create HATEOAS links
        List<EntityModel<Matiere>> matieresEntityModels = matieres.stream()
                .map(matiere -> EntityModel.of(matiere,
                        linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, matiere.getId())).withSelfRel()))
                .collect(Collectors.toList());

        // Return the collection of Matieres with HATEOAS links
        return CollectionModel.of(matieresEntityModels,
                linkTo(methodOn(EtudiantController.class).getMatieresByEtudiant(id)).withSelfRel());
    }

    // GET: Single Matiere for Etudiant
    @GetMapping("/etudiants/{id}/matieres/{idMatiere}")
    public EntityModel<Matiere> oneWithMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
        // Use the service method to find the Matiere by its ID for the given Etudiant
        Matiere matiere = etudiantService.findMatiereById(id, idMatiere);

        // Return the Matiere wrapped in EntityModel with HATEOAS links
        return EntityModel.of(matiere,
                linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, idMatiere)).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
    }

    // PUT: Update Matiere
    @PutMapping("/etudiants/{id}/matieres/{idMatiere}")
    public EntityModel<Matiere> updateMatiere(@PathVariable Long id, @PathVariable Long idMatiere, @RequestBody Matiere updatedMatiere) {
        // Use the service to update the Matiere
        Etudiant updatedEtudiant = etudiantService.updateMatiere(id, idMatiere, updatedMatiere);

        // After the Matiere is updated, find the updated Matiere in the Etudiant's matieres list
        Matiere updatedMatiereInEtudiant = updatedEtudiant.getMatieres().stream()
                .filter(m -> m.getId().equals(idMatiere))
                .findFirst()
                .orElseThrow(() -> new MatiereNotFoundException(idMatiere));

        // Return the updated Matiere wrapped in an EntityModel with HATEOAS links
        return EntityModel.of(updatedMatiereInEtudiant,
                linkTo(methodOn(EtudiantController.class).oneWithMatiere(id, idMatiere)).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
    }


    // DELETE: Remove Matiere
    @DeleteMapping("/etudiants/{id}/matieres/{idMatiere}")
    public EntityModel<String> deleteMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
        // Use the service to delete the Matiere for the Etudiant
        Etudiant updatedEtudiant = etudiantService.deleteMatiere(id, idMatiere);

        // Return a response message with HATEOAS link to the Etudiant
        return EntityModel.of("Matière supprimée",
                linkTo(methodOn(EtudiantController.class).one(id)).withRel("etudiant"));
    }

    }

