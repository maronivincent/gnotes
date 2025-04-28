package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.service.ControlesService;

@RestController

public class ControlesController {
    
    private final ControlesService controleService;

    ControlesController(ControlesService controleService) {
        this.controleService = controleService;
    }

    
    // POST: Add Controle to Etudiant
    @PostMapping("/controles")
    public EntityModel<Controle> newControle(@RequestBody Controle controle) {
        // Call the service method to add Matieres (Controles) to the Etudiant
        Controle savedControle = controleService.saveControle(controle);
        
        // Return the updated Matiere (Controle) wrapped in EntityModel with HATEOAS links
        
        return EntityModel.of(savedControle,
                linkTo(methodOn(ControlesController.class).getControleById(savedControle.getId())).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    // GET: List all Matieres (Controles)
    @GetMapping("/controles")
    CollectionModel<EntityModel<Controle>> all() {
        List<EntityModel<Controle>> controles = StreamSupport.stream(controleService.getAllControles().spliterator(), false)
                .map((Controle controle) -> EntityModel.of(controle,
                        linkTo(methodOn(ControlesController.class).getControleById(controle.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(controles,
                linkTo(methodOn(ControlesController.class).all()).withSelfRel());
    }

 // GET: Retrieve a single User by ID
    @GetMapping("/controles/{id}")
    EntityModel<Controle> getControleById(@PathVariable Long id) {
        Controle controle = controleService.getContoleById(id);
        
        return EntityModel.of(controle,
                linkTo(methodOn(ControlesController.class).getControleById(id)).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    // PUT: Update an existing User by ID
    @PutMapping("/controles/{id}")
    public EntityModel<Controle> updateControle(@PathVariable Long id, @RequestBody Controle updatedControle) {
        Controle  controle= controleService.updateControle(id, updatedControle);

        return EntityModel.of(controle,
                linkTo(methodOn(ControlesController.class).getControleById(controle.getId())).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    // DELETE: Delete a User by ID
    @DeleteMapping("/controles/{id}")
    EntityModel<Map<String, Object>> deleteControle(@PathVariable Long id) {
        Controle deletedControle = controleService.deleteControle(id);
        
        String message = "Controle supprimé: ";
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("controle", deletedControle);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }
}


