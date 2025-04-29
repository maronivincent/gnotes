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
import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.MatiereRepository;
import com.saintsau.slam2.gnotes30.service.EtudiantService;
import com.saintsau.slam2.gnotes30.service.MatiereService;




@RestController
public class MatiereController {
    private final MatiereService matiereService;


    public MatiereController(MatiereService matiereService) {
            super();
        this.matiereService = matiereService;
    }
    

    // POST: Add a new Matiere
    @PostMapping("/matieres")
    EntityModel<Matiere> newMatiere(@RequestBody Matiere matiere) {
        Matiere savedMatiere = matiereService.saveMatiere(matiere);

        return EntityModel.of(savedMatiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(savedMatiere.getId())).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    // GET: Retrieve all Matieres
    @GetMapping("/matieres")
    CollectionModel<EntityModel<Matiere>> all() {
        List<EntityModel<Matiere>> matieres = StreamSupport.stream(matiereService.findAllMatiere().spliterator(), false)
                .map((Matiere matiere) -> EntityModel.of(matiere,
                        linkTo(methodOn(UserController.class).getUserById(matiere.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(matieres,
                linkTo(methodOn(MatiereController.class).all()).withSelfRel());
    }

    // GET: Retrieve a single User by ID
    @GetMapping("/matieres/{id}")
    EntityModel<Matiere> getMatiereById(@PathVariable Long id) {
        Matiere matiere = matiereService.getMatiereById(id);
        
        return EntityModel.of(matiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(id)).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    // PUT: Update an existing User by ID
    @PutMapping("/matieres/{id}")
    public EntityModel<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere updatedMatiere) {
        Matiere matiere = matiereService.updateMatiere(id, updatedMatiere);

        return EntityModel.of(matiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(matiere.getId())).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    // DELETE: Delete a User by ID
    @DeleteMapping("/matieres/{id}")
    EntityModel<Map<String, Object>> deleteMatiere(@PathVariable Long id) {
        Matiere deletedMatiere = matiereService.deleteMatiere(id);
        
        String message = "Matiere supprimé: " + deletedMatiere.getIntitule();
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("etudiant", deletedMatiere);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }
}

