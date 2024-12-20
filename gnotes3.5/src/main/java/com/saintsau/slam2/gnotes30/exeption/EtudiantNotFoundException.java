package com.saintsau.slam2.gnotes30.exeption;

public class EtudiantNotFoundException extends RuntimeException {
	
    public EtudiantNotFoundException(Long id) {
        super("Could not find Etudiant with id: " + id);
    }
}
