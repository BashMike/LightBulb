package model.GameCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.Cells.StandardCell;

public class TEST_GameElement {
	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation
	//
	// 2. Position.
	// 2.1. Checking of having position;
	// 2.1.1. Game element is set to cell;
	// 2.1.2. Game element is removed from cell;
	// 2.2. Getting position.
	// 2.2.1. Unsuccessful getting 	- Game element've just created;
	// 2.2.2. Successful getting 	- Game element is set to cell;
	// 2.2.3. Unsuccessful getting 	- Game element is removed from cell.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		GameElement sewer = new Sewer();

		// Run testing position AND check needed state of entity ...
		// ... check that sewer hasn't position
		Assertions.assertFalse(sewer.hasPosition());
		 */
	}

	// ========== POSITION ==========
	// --- CHECK OF HAVING POSITION ---
	// game element is set to cell
	@Test
	void hasPosition_isSetToCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell = new StandardCell();
		GameElement sewer = new Sewer();

		cell.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that sewer has position
		Assertions.assertTrue(sewer.hasPosition());
		 */
	}

	// game element is removed from cell
	@Test
	void hasPosition_isRemovedFromCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell = new StandardCell();
		GameElement sewer = new Sewer();

		cell.placeGameElement(sewer);
		cell.removeGameElement(sewer);

		// Run testing operation AND check needed state of entity ...
		// ... check that sewer hasn't position
		Assertions.assertFalse(sewer.hasPosition());
		 */
	}

	// --- GET POSITION ---
	// game element has just been created
	@Test
	void getPosition_isntSetPosition() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		GameElement sewer = new Sewer();

		// Run testing operation
		Exception exception = Assertions.assertThrows(RuntimeException.class, sewer::position);

		// Check needed state of entity ...
		// ... check that getting position of unset sewer causes an exception
		Assertions.assertNotEquals("", exception.getMessage());
		 */
	}

	// game element is set to cell
	@Test
	void getPosition_isSetToCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell = new StandardCell();
		GameElement sewer = new Sewer();

		cell.placeGameElement(sewer);

		// Run testing operation AND check needed state of entity ...
		// ... check that sewer's position equals an initial cell
		Assertions.assertEquals(cell, sewer.position());
		 */
	}

	// game element is removed from cell
	@Test
	void getPosition_isRemovedFromCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell = new StandardCell();
		GameElement sewer = new Sewer();

		cell.placeGameElement(sewer);
		cell.removeGameElement(sewer);

		// Run testing operation
		Exception exception = Assertions.assertThrows(RuntimeException.class, sewer::position);

		// Check needed state of entity ...
		// ... check that getting position of unset sewer causes an exception
		Assertions.assertNotEquals("", exception.getMessage());
		 */
	}
}
