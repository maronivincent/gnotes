package com.saintsau.slam2.gnotes30;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;

@RestController
public class EtudiantController {
	private final EtudiantRepository etudiantRepository;
	private final MatiereRepository matiereRepository;

	public EtudiantController(EtudiantRepository etudiantRepository, MatiereRepository matiereRepository) {
		super();
		this.etudiantRepository = etudiantRepository;
		this.matiereRepository = matiereRepository;
		
	}
	
	//curl -X POST localhost:8080/etudiants -H 'Content-type:application/json' -d '{"nom":"John"}'
	//curl -X POST localhost:8080/etudiants -H 'Content-type:application/json' -d '{"nom":"Lea"}'
	@PostMapping("/etudiants")
	Etudiant newEtudiant(@RequestBody Etudiant etudiant) {
		return etudiantRepository.save(etudiant);
	}
	
	//curl -v localhost:8080/etudiants | json_pp
	@GetMapping("/etudiants")
	List<Etudiant> all(){
		return (List<Etudiant>) etudiantRepository.findAll();
	}
	
	//curl -v localhost:8080/etudiants/1 | json_pp
	@GetMapping("/etudiants/{id}")
	Etudiant one(@PathVariable Long id) {
	    return etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));
	}

	
	//curl -X PUT localhost:8080/etudiants/1 -H 'Content-type:application/json' -d '{"nom":"Updated Name"}'
	@PutMapping("/etudiants/{id}")
	Etudiant updateEtudiant(@PathVariable Long id, @RequestBody Etudiant updatedEtudiant) {
	    Etudiant etudiant = etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));

	    etudiant.setNom(updatedEtudiant.getNom());
	    // Update other fields as needed
	    return etudiantRepository.save(etudiant);
	}

	
	//curl -X DELETE localhost:8080/etudiants/1
	@DeleteMapping("/etudiants/{id}")
	String deleteEtudiant(@PathVariable Long id) {
	    Etudiant etudiant = etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));

	    // Delete associated Matieres
	    for (Matiere matiere : etudiant.getMatieres()) {
	        matiereRepository.delete(matiere);
	    }

	    etudiantRepository.deleteById(id);
	    return "Etudiant supprimé";
	}

	
	//curl -X POST localhost:8080/etudiants/1/matieres -H 'Content-type:application/json' -d '[{"intitule":"maths","type":"DS","coef":3,"note":13.50}]'
	@PostMapping("/etudiants/{id}/matieres")
	Etudiant newMatierePerEtudiant(@PathVariable Long id, @RequestBody Set<Matiere> matieres) {
		Etudiant etudiant = etudiantRepository.findById(id).get();
		
		for(Matiere matiere : matieres){
			matiere.setEtudiant(etudiant);
		}
		
		etudiant.setMatieres(matieres);
		
		return etudiantRepository.save(etudiant);
	}
	
	
	
	//curl -v localhost:8080/etudiants/1/matieres/1 | json_pp
	@GetMapping("/etudiants/{id}/matieres/{idMatiere}")
	Matiere oneWithMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
	    Etudiant etudiant = etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));

	    return etudiant.getMatieres().stream()
	            .filter(matiere -> matiere.getId().equals(idMatiere))
	            .findFirst()
	            .orElseThrow(() -> new MatiereNotFoundException(idMatiere));
	}

	
	//curl -X PUT localhost:8080/etudiants/1/matieres/1 -H 'Content-type:application/json' -d '{"intitule":"Updated Maths","type":"Exam","coef":4,"note":15.0}'
	@PutMapping("/etudiants/{id}/matieres/{idMatiere}")
	Matiere updateMatiere(@PathVariable Long id, @PathVariable Long idMatiere, @RequestBody Matiere updatedMatiere) {
	    Etudiant etudiant = etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));

	    Matiere matiere = etudiant.getMatieres().stream()
	            .filter(m -> m.getId().equals(idMatiere))
	            .findFirst()
	            .orElseThrow(() -> new MatiereNotFoundException(idMatiere));

	    matiere.setIntitule(updatedMatiere.getIntitule());
	    matiere.setType(updatedMatiere.getType());
	    matiere.setCoef(updatedMatiere.getCoef());
	    matiere.setNote(updatedMatiere.getNote());

	    return matiereRepository.save(matiere);
	}

	
	//curl -X DELETE localhost:8080/etudiants/1/matieres/1
	@DeleteMapping("/etudiants/{id}/matieres/{idMatiere}")
	String deleteMatiere(@PathVariable Long id, @PathVariable Long idMatiere) {
	    Etudiant etudiant = etudiantRepository.findById(id)
	            .orElseThrow(() -> new EtudiantNotFoundException(id));

	    Matiere matiere = etudiant.getMatieres().stream()
	            .filter(m -> m.getId().equals(idMatiere))
	            .findFirst()
	            .orElseThrow(() -> new MatiereNotFoundException(idMatiere));

	    etudiant.getMatieres().remove(matiere);
	    matiereRepository.delete(matiere);
	    etudiantRepository.save(etudiant);

	    return "Matière supprimé";
	}


}
