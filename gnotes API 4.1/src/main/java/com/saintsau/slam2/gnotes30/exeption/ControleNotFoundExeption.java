package com.saintsau.slam2.gnotes30.exeption;

/**
 * Exception personnalisée pour indiquer qu'un contrôle n'a pas été trouvé.
 * Cette exception est lancée lorsqu'un contrôle avec un identifiant spécifique
 * n'a pas pu être localisé dans le système.
 * 
 * @author [Votre Nom]
 */
public class ControleNotFoundExeption extends RuntimeException {
    
    /**
     * Constructeur pour l'exception ControleNotFoundExeption.
     * 
     * @param id L'identifiant du contrôle qui n'a pas été trouvé.
     */
    public ControleNotFoundExeption(Long id) {
        super("Could not find Controle with id: " + id);
    }
}
