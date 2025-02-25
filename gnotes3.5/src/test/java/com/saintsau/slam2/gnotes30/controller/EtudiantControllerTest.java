package com.saintsau.slam2.gnotes30.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.MatiereRepository;
import com.saintsau.slam2.gnotes30.service.EtudiantService;

@WebMvcTest(controllers = EtudiantController.class)
class EtudiantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MatiereRepository matiereRepository;

	@MockBean
	private EtudiantService service;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAll() throws Exception {
	    // Arrange
	    Etudiant dimitri = new Etudiant("Dimitri");
	    Etudiant jean = new Etudiant("Jean");
	    Etudiant lea = new Etudiant("Lea");
	    List<Etudiant> allEtudiants = Arrays.asList(dimitri, jean, lea);

	    List<EntityModel<Etudiant>> entityModels = allEtudiants.stream()
	            .map(etudiant -> EntityModel.of(etudiant,
	                    linkTo(methodOn(EtudiantController.class).one(etudiant.getNumero())).withSelfRel()))
	            .collect(Collectors.toList());

	    CollectionModel<EntityModel<Etudiant>> collectionModel = CollectionModel.of(entityModels,
	            linkTo(methodOn(EtudiantController.class).all()).withSelfRel());

	    String expectedJson = new ObjectMapper().writeValueAsString(collectionModel);

	    when(service.findAllEtudiant()).thenReturn(allEtudiants);

	    // Act & Assert
	    this.mockMvc.perform(get("/etudiants"))
	            .andExpect(status().isOk())
	            .andExpect(content().json(expectedJson));
	}



	@Test
	public void testOne() throws Exception {
		// Arrange
		when(service.findEtudiantById(99L)).thenThrow(EtudiantNotFoundException.class);

		// Act & Assert
		this.mockMvc.perform(get("/etudiants/99")).andExpect(status().isNotFound());
	}

	@Test
	public void testCreateEtudiant() throws Exception {
		// Arrange
		Etudiant newEtudiant = new Etudiant("Marie");
		Etudiant savedEtudiant = new Etudiant("Marie");
		savedEtudiant.setNumero(1L);

		when(service.saveEtudiant(newEtudiant)).thenReturn(savedEtudiant);

		// Act & Assert
		this.mockMvc
				.perform(post("/etudiants").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(newEtudiant)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("Marie"))
				.andExpect(jsonPath("$.numero").value(1L));
	}

	@Test
	public void testUpdateEtudiant() throws Exception {
		// Arrange
		Etudiant updatedEtudiant = new Etudiant("Paul");
		updatedEtudiant.setNumero(1L);

		when(service.updateEtudiant(1L, updatedEtudiant)).thenReturn(updatedEtudiant);

		// Act & Assert
		this.mockMvc
				.perform(put("/etudiants/1").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(updatedEtudiant)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("Paul"))
				.andExpect(jsonPath("$.numero").value(1L));
	}

	@Test
	public void testDeleteEtudiant() throws Exception {
		// Arrange
		Etudiant deletedEtudiant = new Etudiant("Marc");
		deletedEtudiant.setNumero(1L);

		when(service.deleteById(1L)).thenReturn(deletedEtudiant);

		// Act & Assert
		this.mockMvc.perform(delete("/etudiants/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Etudiant supprimé: Marc"));
	}

	@Test
	public void testAddMatieresToEtudiant() throws Exception {
		// Arrange
		Matiere math = new Matiere("Mathématiques", 0);
		Matiere science = new Matiere("Sciences", 0);
		Set<Matiere> matieres = Set.of(math, science);

		Etudiant updatedEtudiant = new Etudiant("Luc");
		updatedEtudiant.setNumero(1L);
		updatedEtudiant.setMatieres(matieres);

		when(service.saveMatiereByEtudiant(1L, matieres)).thenReturn(updatedEtudiant);

		// Act & Assert
		this.mockMvc
				.perform(post("/etudiants/1/matieres").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(matieres)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("Luc"))
				.andExpect(jsonPath("$.matieres[*].nom").value(containsString("Mathématiques")))
				.andExpect(jsonPath("$.matieres[*].nom").value(containsString("Sciences")));
	}

	@Test
	public void testDeleteMatiere() throws Exception {
		// Arrange
		Etudiant updatedEtudiant = new Etudiant("Luc");
		updatedEtudiant.setNumero(1L);

		when(service.deleteMatiere(1L, 2L)).thenReturn(updatedEtudiant);

		// Act & Assert
		this.mockMvc.perform(delete("/etudiants/1/matieres/2")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Matière supprimée")));
	}
}
