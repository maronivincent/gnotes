package com.saintsau.slam2.gnotes3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.GnotesDBManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows non-static @BeforeAll
class GnotesDBManagerTest {
    private GnotesDBManager stockManager;

    @BeforeAll
    void testinitialize() {
        stockManager = new GnotesDBManager();
        stockManager.initialize();
    }

    @Test
    void testInsertDataInDB() {
        // Test insertDataInDB logic
        stockManager.insertDataInDB();

        // Assertions for student 1
        assertNotEquals(null, stockManager.getEtudiantInDB(1));
        assertEquals("Dimitri", stockManager.getEtudiantInDB(1).getNom());

        // Assertions for student 2
        assertNotEquals(null, stockManager.getEtudiantInDB(2));
        assertEquals("jean", stockManager.getEtudiantInDB(2).getNom());

        // Assertions for student 3
        assertNotEquals(null, stockManager.getEtudiantInDB(3));
        assertEquals("lea", stockManager.getEtudiantInDB(3).getNom());

        // Testing majorDePromotion()
        Etudiant dimitri = stockManager.getEtudiantInDB(1);
        Etudiant jean = stockManager.getEtudiantInDB(2);
        Etudiant lea = stockManager.getEtudiantInDB(3);

        ArrayList<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(dimitri);
        etudiants.add(jean);
        etudiants.add(lea);

        ArrayList<Etudiant> majors = Etudiant.majorDePromotion(etudiants);

        // Assert
        assertEquals("[Etudiant [nom=lea, numero=3, moyenne=12.0]]", majors.toString());
    }
}
