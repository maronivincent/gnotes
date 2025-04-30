package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
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


    public EtudiantController(EtudiantService etudiantService) {
            super();
        this.etudiantService = etudiantService;
    }
    

    // POST: Add a new Etudiant
    @PostMapping("/etudiants")
    public ResponseEntity<EntityModel<Etudiant>> newEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);

        EntityModel<Etudiant> entityModel = EntityModel.of(savedEtudiant,
                linkTo(methodOn(EtudiantController.class).one(savedEtudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
        
        // Return the EntityModel with a 201 Created status and the Location header pointing to the newly created resource
        return ResponseEntity
                .created(URI.create(entityModel.getRequiredLink("self").getHref())) // Location header
                .body(entityModel); // The body contains the resource representation
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

}


