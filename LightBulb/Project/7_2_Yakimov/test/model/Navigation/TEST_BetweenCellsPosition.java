package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_BetweenCellsPosition {
	// ============ test part ============
	// --- tests short description ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - normalized creation;
	// 1.1.2. Successful creation - other normalized creation;
	// 1.1.3. Successful creation - not normalized creation;
	// 1.1.4. Successful creation - other not normalized creation.
	//
    // 2. Attributes;
	// 2.1. Get cell;
	// 2.1.1. Send one cell position to two between cells position.
    // 2.2. Get direction;
	// 2.2.1. Send one direction to two between cells position.
	// 2.3. Check orientation.
	// 2.3.1. Normalized vertical between cells position;
	// 2.3.2. Normalized horizontal between cells position;
	// 2.3.3. Not normalized vertical between cells position;
	// 2.3.4. Not normalized horizontal between cells position.
	//
	// 3. Get object in direction.
	// 3.1. Get cell position in direction;
    // 3.1.1. Unsuccessful getting 	- get cell position in up direction from vertical orientated between cells position;
	// 3.1.2. Unsuccessful getting 	- get cell position in down direction from vertical orientated between cells position;
	// 3.1.3. Successful getting 	- get cell position in right direction from vertical orientated between cells position;
	// 3.1.4. Successful getting 	- get cell position in left direction from vertical orientated between cells position;
	// 3.1.5. Successful getting 	- get cell position in up direction from horizontal orientated between cells position;
	// 3.1.6. Successful getting 	- get cell position in down direction from horizontal orientated between cells position;
	// 3.1.7. Unsuccessful getting 	- get cell position in right direction from horizontal orientated between cells position;
	// 3.1.8. Unsuccessful getting 	- get cell position in left direction from horizontal orientated between cells position.
	// 3.2. Get next between cells position in direction.
	// 3.2.1. Get next between cells position in up direction from vertical orientated between cells position;
	// 3.2.2. Get next between cells position in down direction from vertical orientated between cells position;
	// 3.2.3. Get next between cells position in right direction from vertical orientated between cells position;
	// 3.2.4. Get next between cells position in left direction from vertical orientated between cells position;
	// 3.2.5. Get next between cells position in up direction from horizontal orientated between cells position;
	// 3.2.6. Get next between cells position in down direction from horizontal orientated between cells position;
	// 3.2.7. Get next between cells position in right direction from horizontal orientated between cells position;
	// 3.2.8. Get next between cells position in left direction from horizontal orientated between cells position.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation (NORMALIZED position)
	@Test
	void basicConstructor_normalizedPosition_1() {
		BetweenCellsPosition betweenPosition = new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);

		Assertions.assertEquals(betweenPosition.cellPosition(), new CellPosition(2, 3));
		Assertions.assertEquals(betweenPosition.direction(), Direction.UP);
	}

	// Successful creation - NORMALIZED position
	@Test
	void basicConstructor_normalizedPosition_2() {
		BetweenCellsPosition betweenPosition = new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);

		Assertions.assertEquals(betweenPosition.cellPosition(), new CellPosition(2, 3));
		Assertions.assertEquals(betweenPosition.direction(), Direction.LEFT);
	}

	// Successful creation - SIMPLE creation (NOT NORMALIZED position)
	@Test
	void basicConstructor_notNormalizedPosition_1() {
		BetweenCellsPosition betweenPosition = new BetweenCellsPosition(new CellPosition(2, 3), Direction.DOWN);

		Assertions.assertEquals(betweenPosition.cellPosition(), new CellPosition(2, 4));
		Assertions.assertEquals(betweenPosition.direction(), Direction.UP);
	}

	// Successful creation - NOT NORMALIZED position
	@Test
	void basicConstructor_notNormalizedPosition_2() {
		BetweenCellsPosition betweenPosition = new BetweenCellsPosition(new CellPosition(2, 3), Direction.RIGHT);

		Assertions.assertEquals(betweenPosition.cellPosition(), new CellPosition(3, 3));
		Assertions.assertEquals(betweenPosition.direction(), Direction.LEFT);
	}

	// ========= ATTRIBUTES =========
	// --- GET CELL POSITION ---
	// send ONE cell position TO TWO between cells positions
	@Test
	void cellPosition_oneCellPosToTwoBetweenPoses() {
		CellPosition cellPos = new CellPosition(2, 3);

		BetweenCellsPosition betweenPosition1 = new BetweenCellsPosition(cellPos, Direction.UP);
		BetweenCellsPosition betweenPosition2 = new BetweenCellsPosition(cellPos, Direction.UP);

		Assertions.assertTrue(betweenPosition1.cellPosition() == betweenPosition2.cellPosition());
	}

	// --- GET DIRECTION ---
	// send ONE direction TO TWO between cells positions
	@Test
	void cellPosition_oneDirectionToTwoBetweenPoses() {
		Direction direction = Direction.UP;

		BetweenCellsPosition betweenPosition1 = new BetweenCellsPosition(new CellPosition(2, 3), direction);
		BetweenCellsPosition betweenPosition2 = new BetweenCellsPosition(new CellPosition(2, 3), direction);

		Assertions.assertTrue(betweenPosition1.direction() == betweenPosition2.direction());
	}

	// --- CHECK ORIENTATION ---
	// NORMALIZED VERTICAL between cells positions
	@Test
	void orientation_normalizedVertical() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(1, 3), Direction.LEFT);
		Assertions.assertTrue(betweenPosition.orientation() == Orientation.VERTICAL);
	}

	// NORMALIZED HORIZONTAL between cells position
	@Test
	void orientation_normalizedHorizontal() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(1, 3), Direction.UP);
		Assertions.assertTrue(betweenPosition.orientation() == Orientation.HORIZONTAL);
	}

	// NOT NORMALIZED VERTICAL between cells position
	@Test
	void orientation_notNormalizedVertical() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(1, 3), Direction.RIGHT);
		Assertions.assertTrue(betweenPosition.orientation() == Orientation.VERTICAL);
	}

	// NOT NORMALIZED HORIZONTAL between cells position
	@Test
	void orientation_notNormalizedHorizontal() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(1, 3), Direction.DOWN);
		Assertions.assertTrue(betweenPosition.orientation() == Orientation.HORIZONTAL);
	}

	// === GET OBJECT IN DIRECTION ===
	// --- GET CELL POSITION IN GIVEN DIRECTION ---
	// Unsuccessful getting - get cell position in UP direction from VERTICAL between cells position
	@Test
	void cellPosition_upDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> betweenPosition.cellPosition(Direction.UP));

		Assertions.assertNotEquals(exception.getMessage(), "");
	}

	// Unsuccessful getting - get cell position in DOWN direction from VERTICAL between cells position
	@Test
	void cellPosition_downDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> betweenPosition.cellPosition(Direction.DOWN));

		Assertions.assertNotEquals(exception.getMessage(), "");
	}

	// Successful getting - get cell position in RIGHT direction from VERTICAL between cells position
	@Test
	void cellPosition_rightDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		CellPosition cell = betweenPosition.cellPosition(Direction.RIGHT);

		Assertions.assertTrue(cell.equals(new CellPosition(2, 3)));
	}

	// Successful getting - get cell position in LEFT direction from VERTICAL between cells position
	@Test
	void cellPosition_leftDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		CellPosition cell = betweenPosition.cellPosition(Direction.LEFT);

		Assertions.assertTrue(cell.equals(new CellPosition(1, 3)));
	}
	// Successful getting - get cell position in UP direction from HORIZONTAL between cells position
	@Test
	void cellPosition_upDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		CellPosition cell = betweenPosition.cellPosition(Direction.UP);

		Assertions.assertTrue(cell.equals(new CellPosition(2, 2)));
	}

	// Successful getting - get cell position in DOWN direction from HORIZONTAL between cells position
	@Test
	void cellPosition_downDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		CellPosition cell = betweenPosition.cellPosition(Direction.DOWN);

		Assertions.assertTrue(cell.equals(new CellPosition(2, 3)));
	}

	// Unsuccessful getting - get cell position in RIGHT direction from HORIZONTAL between cells position
	@Test
	void cellPosition_rightDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> betweenPosition.cellPosition(Direction.RIGHT));

		Assertions.assertNotEquals(exception.getMessage(), "");
	}

	// Unsuccessful getting - get cell position in LEFT direction from HORIZONTAL between cells position
	@Test
	void cellPosition_leftDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPosition= new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> betweenPosition.cellPosition(Direction.LEFT));

		Assertions.assertNotEquals(exception.getMessage(), "");
	}

	// --- GET NEXT BETWEEN CELLS POSITION IN GIVEN DIRECTION ---
	// Get next between cells position in UP direction from VERTICAL between cells position
	@Test
	void next_upDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.UP);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(2, 2), Direction.LEFT)));
	}

	// Get next between cells position in DOWN direction from VERTICAL between cells position
	@Test
	void next_downDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.DOWN);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(2, 4), Direction.LEFT)));
	}

	// Get next between cells position in RIGHT direction from VERTICAL between cells position
	@Test
	void next_rightDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.RIGHT);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(3, 3), Direction.LEFT)));
	}

	// Get next between cells position in LEFT direction from VERTICAL between cells position
	@Test
	void next_leftDirectionFromVerticalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.LEFT);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.LEFT);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(1, 3), Direction.LEFT)));
	}

	// Get next between cells position in UP direction from HORIZONTAL between cells position
	@Test
	void next_upDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.UP);
		BetweenCellsPosition betweenPos3 = new BetweenCellsPosition(new CellPosition(2, 2), Direction.UP);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(2, 2), Direction.UP)));
	}

	// Get next between cells position in DOWN direction from HORIZONTAL between cells position
	@Test
	void next_downDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.DOWN);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(2, 4), Direction.UP)));
	}

	// Get next between cells position in RIGHT direction from HORIZONTAL between cells position
	@Test
	void next_rightDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.RIGHT);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(3, 3), Direction.UP)));
	}

	// Get next between cells position in LEFT direction from HORIZONTAL between cells position
	@Test
	void next_leftDirectionFromHorizontalOrientation() {
		BetweenCellsPosition betweenPos1 = new BetweenCellsPosition(new CellPosition(2, 3), Direction.UP);
		BetweenCellsPosition betweenPos2 = betweenPos1.next(Direction.LEFT);

		Assertions.assertTrue(betweenPos2.equals(new BetweenCellsPosition(new CellPosition(1, 3), Direction.UP)));
	}
}
