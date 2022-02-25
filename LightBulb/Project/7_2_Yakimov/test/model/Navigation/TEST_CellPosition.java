package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_CellPosition {
	// ============ test part ============
	// --- tests short description ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
    // 2. Neighbours.
	// 2.1. Get next cell position.
	// 2.1.1. Get up cell position;
	// 2.1.2. Get down cell position;
	// 2.1.3. Get right cell position;
	// 2.1.4. Get left cell position.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		CellPosition cellPosition = new CellPosition(2, 3);

		Assertions.assertEquals(cellPosition.x(), 2);
		Assertions.assertEquals(cellPosition.y(), 3);
	}

	// ========= NEIGHBOURS =========
	// --- GET NEXT CELL POSITION ---
	// Get UP cell position
	@Test
	void next_upCellPosition() {
		CellPosition cellPosition = new CellPosition(2, 3);
		CellPosition upCellPos = cellPosition.next(Direction.UP);

		Assertions.assertEquals(upCellPos.x(), 2);
		Assertions.assertEquals(upCellPos.y(), 2);
	}

	// Get DOWN cell position
	@Test
	void next_downCellPosition() {
		CellPosition cellPosition = new CellPosition(2, 3);
		CellPosition downCellPos = cellPosition.next(Direction.DOWN);

		Assertions.assertEquals(downCellPos.x(), 2);
		Assertions.assertEquals(downCellPos.y(), 4);
	}

	// Get RIGHT cell position
	@Test
	void next_rightCellPosition() {
		CellPosition cellPosition = new CellPosition(2, 3);
		CellPosition rightCellPos = cellPosition.next(Direction.RIGHT);

		Assertions.assertEquals(rightCellPos.x(), 3);
		Assertions.assertEquals(rightCellPos.y(), 3);
	}

	// Get LEFT cell position
	@Test
	void next_leftCellPosition() {
		CellPosition cellPosition = new CellPosition(2, 3);
		CellPosition leftCellPos = cellPosition.next(Direction.LEFT);

		Assertions.assertEquals(leftCellPos.x(), 1);
		Assertions.assertEquals(leftCellPos.y(), 3);
	}
}
