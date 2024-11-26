package com.saintsau.slam2.gnotes3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.saintsau.slam2.gnotes3.Matiere;

class MatiereTest {

	@Test
	void testToString() {
		// Before
		String intitule = "Informatique";
		int coef = 12;
		String type = "Devoir écrit";
		Matiere matiere = new Matiere(intitule, coef, type);

		// methode à tester
		String toString = matiere.toString();

		// Assert
		assertEquals("La Matiere Informatique de type Devoir écrit est de coef= 12", toString);

		// Before
		type = "TP";
		matiere.setType(type);

		// methode à tester
		toString = matiere.toString();

		// assert
		assertEquals("La Matiere Informatique de type TP est de coef= 12", toString);

		// Before
		type = "Oral";
		matiere.setType(type);

		// methode à tester
		toString = matiere.toString();

		// assert
		assertEquals("La Matiere Informatique de type Oral est de coef= 12", toString);
	}

}
