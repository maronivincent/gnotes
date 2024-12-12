package com.saintsau.slam2.gnotes30.exeption;

public class MatiereNotFoundException extends RuntimeException {
    public MatiereNotFoundException(Long idMatiere) {
        super("Could not find Matiere with id: " + idMatiere);
    }
}
