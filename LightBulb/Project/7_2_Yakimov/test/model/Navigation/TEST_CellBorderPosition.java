package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_CellBorderPosition {
    // ============ test part ============
    // --- tests short description ---
    // 1. Creation;
    // 1.1. Simple creation.
    // 1.1.1. Successful creation   - simple creation;
    // 1.1.2. Successful creation   - creation with vertical denormalised direction;
    // 1.1.3. Successful creation   - creation with horizontal denormalised direction;
    // 1.1.4. Successful creation   - creation with both denormalised directions;
    // 1.1.5. Unsuccessful creation - creation with only vertical directions;
    // 1.1.6. Unsuccessful creation - creation with only horizontal directions;
    // 1.1.7. Unsuccessful creation - creation with only vertical directions which are the same;
    // 1.1.8. Unsuccessful creation - creation with only horizontal directions which are the same.
    //
    // 2. Attributes;
    // 2.1. Get cell;
    // 2.1.1. Send one cell position to two cell border position.
    // 2.2. Get directions;
    // 2.2.1. Send one vertical direction to two cell border position;
    // 2.2.2. Send one horizontal direction to two cell border position.
    //
    // 3. Get object in direction.
    // 3.1. Get between cells position in direction;
    // 3.1.1. Get between cells position in up direction from cell border position;
    // 3.1.2. Get between cells position in down direction from cell border position;
    // 3.1.3. Get between cells position in right direction from cell border position;
    // 3.1.4. Get between cells position in left direction from cell border position.
    // 3.2. Get next cell border position in direction.
    // 3.2.1. Get next cell border position in up direction from cell border position;
    // 3.2.2. Get next cell border position in down direction from cell border position;
    // 3.2.3. Get next cell border position in right direction from cell border position;
    // 3.2.4. Get next cell border position in left direction from cell border position.

    // ========== CREATION ==========
    // --- SIMPLE CREATION ---
    // Successful creation - SIMPLE creation
    @Test
    void basicConstructor_simpleCreation() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);

        Assertions.assertEquals(cellBorderPosition.cellPosition(), new CellPosition(2, 2));
        Assertions.assertEquals(cellBorderPosition.verticalDirection(), Direction.UP);
        Assertions.assertEquals(cellBorderPosition.horizontalDirection(), Direction.LEFT);
    }

    // Successful creation - creation with VERTICAL DENORMALISED DIRECTION
    @Test
    void basicConstructor_creationWithVerticalDenormalizedDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.DOWN, Direction.LEFT);

        Assertions.assertEquals(cellBorderPosition.cellPosition(), new CellPosition(2, 3));
        Assertions.assertEquals(cellBorderPosition.verticalDirection(), Direction.UP);
        Assertions.assertEquals(cellBorderPosition.horizontalDirection(), Direction.LEFT);
    }

    // Successful creation - creation with HORIZONTAL DENORMALISED DIRECTION
    @Test
    void basicConstructor_creationWithHorizontalDenormalizedDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.RIGHT, Direction.UP);

        Assertions.assertEquals(cellBorderPosition.cellPosition(), new CellPosition(3, 2));
        Assertions.assertEquals(cellBorderPosition.verticalDirection(), Direction.UP);
        Assertions.assertEquals(cellBorderPosition.horizontalDirection(), Direction.LEFT);
    }

    // Successful creation - creation with BOTH DENORMALISED DIRECTIONS
    @Test
    void basicConstructor_creationWithBothDenormalizedDirections() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.RIGHT, Direction.DOWN);

        Assertions.assertEquals(cellBorderPosition.cellPosition(), new CellPosition(3, 3));
        Assertions.assertEquals(cellBorderPosition.verticalDirection(), Direction.UP);
        Assertions.assertEquals(cellBorderPosition.horizontalDirection(), Direction.LEFT);
    }

    // Unsuccessful creation - creation with ONLY VERTICAL DIRECTIONS
    @Test
    void basicConstructor_creationWithOnlyVerticalDirections() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.DOWN));
        Assertions.assertNotEquals(exception.getMessage(), "");
    }

    // Unsuccessful creation - creation with ONLY HORIZONTAL DIRECTIONS
    @Test
    void basicConstructor_creationWithOnlyHorizontalDirections() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> new CellBorderPosition(new CellPosition(2, 2), Direction.RIGHT, Direction.LEFT));
        Assertions.assertNotEquals(exception.getMessage(), "");
    }

    // Unsuccessful creation - creation with ONLY VERTICAL DIRECTIONS which are SAME
    @Test
    void basicConstructor_creationWithOnlyVerticalDirectionsWhichAreSame() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.UP));
        Assertions.assertNotEquals(exception.getMessage(), "");
    }

    // Unsuccessful creation - creation with ONLY HORIZONTAL DIRECTIONS which are SAME
    @Test
    void basicConstructor_creationWithOnlyHorizontalDirectionsWhichAreSame() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> new CellBorderPosition(new CellPosition(2, 2), Direction.RIGHT, Direction.RIGHT));
        Assertions.assertNotEquals(exception.getMessage(), "");
    }

    // ========= ATTRIBUTES =========
    // --- GET CELL POSITION ---
    // send ONE cell position TO TWO cell border positions
    @Test
    void cellPosition_oneCellPosToTwoBetweenPoses() {
        CellPosition cellPos = new CellPosition(2, 3);

        CellBorderPosition cellBorderPosition1 = new CellBorderPosition(cellPos, Direction.UP, Direction.LEFT);
        CellBorderPosition cellBorderPosition2 = new CellBorderPosition(cellPos, Direction.UP, Direction.LEFT);

        Assertions.assertTrue(cellBorderPosition1.cellPosition() == cellBorderPosition2.cellPosition());
    }

    // --- GET DIRECTION ---
    // send ONE VERTICAL direction TO TWO cell border positions
    @Test
    void cellPosition_oneVerticalDirectionToTwoBetweenPoses() {
        Direction direction = Direction.UP;

        CellBorderPosition cellBorderPosition1 = new CellBorderPosition(new CellPosition(2, 2), direction, Direction.LEFT);
        CellBorderPosition cellBorderPosition2 = new CellBorderPosition(new CellPosition(2, 2), Direction.LEFT, direction);

        Assertions.assertTrue(cellBorderPosition1.verticalDirection() == cellBorderPosition2.verticalDirection());
    }

    // send ONE HORIZONTAL direction TO TWO cell border positions
    @Test
    void cellPosition_oneHorizontalDirectionToTwoBetweenPoses() {
        Direction direction = Direction.LEFT;

        CellBorderPosition cellBorderPosition1 = new CellBorderPosition(new CellPosition(2, 2), direction, Direction.UP);
        CellBorderPosition cellBorderPosition2 = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, direction);

        Assertions.assertTrue(cellBorderPosition1.verticalDirection() == cellBorderPosition2.verticalDirection());
    }

    // === GET OBJECT IN DIRECTION ===
    // --- GET BETWEEN CELLS POSITION IN GIVEN DIRECTION ---
    // Get between cells position in UP direction from cell border position
    @Test
    void cellPosition_rightDirectionFromVerticalOrientation() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        BetweenCellsPosition betweenCellsPosition = cellBorderPosition.betweenCellsPosition(Direction.UP);

        BetweenCellsPosition expBetweenCellsPosition = new BetweenCellsPosition(new CellPosition(2, 1), Direction.LEFT);

        Assertions.assertEquals(expBetweenCellsPosition, betweenCellsPosition);
    }

    // Get between cells position in DOWN direction from cell border position
    @Test
    void cellPosition_leftDirectionFromVerticalOrientation() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        BetweenCellsPosition betweenCellsPosition = cellBorderPosition.betweenCellsPosition(Direction.DOWN);

        BetweenCellsPosition expBetweenCellsPosition = new BetweenCellsPosition(new CellPosition(2, 2), Direction.LEFT);

        Assertions.assertEquals(expBetweenCellsPosition, betweenCellsPosition);
    }

    // Get between cells position in RIGHT direction from cell border position
    @Test
    void cellPosition_upDirectionFromHorizontalOrientation() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        BetweenCellsPosition betweenCellsPosition = cellBorderPosition.betweenCellsPosition(Direction.RIGHT);

        BetweenCellsPosition expBetweenCellsPosition = new BetweenCellsPosition(new CellPosition(2, 2), Direction.UP);

        Assertions.assertEquals(expBetweenCellsPosition, betweenCellsPosition);
    }

    // Get between cells position in LEFT direction from cell border position
    @Test
    void cellPosition_downDirectionFromHorizontalOrientation() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        BetweenCellsPosition betweenCellsPosition = cellBorderPosition.betweenCellsPosition(Direction.LEFT);

        BetweenCellsPosition expBetweenCellsPosition = new BetweenCellsPosition(new CellPosition(1, 2), Direction.UP);

        Assertions.assertEquals(expBetweenCellsPosition, betweenCellsPosition);
    }

    // --- GET NEXT CELL BORDER POSITION IN GIVEN DIRECTION ---
    // Get next cell border position in UP direction from cell border position;
    @Test
    void next_fromUpDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        CellBorderPosition nextCellBorderPosition = cellBorderPosition.next(Direction.UP);

        CellBorderPosition expNextCellBorderPosition = new CellBorderPosition(new CellPosition(2, 1), Direction.UP, Direction.LEFT);

        Assertions.assertEquals(expNextCellBorderPosition, nextCellBorderPosition);
    }

    // Get next cell border position in DOWN direction from cell border position;
    @Test
    void next_fromDownDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        CellBorderPosition nextCellBorderPosition = cellBorderPosition.next(Direction.DOWN);

        CellBorderPosition expNextCellBorderPosition = new CellBorderPosition(new CellPosition(2, 3), Direction.UP, Direction.LEFT);

        Assertions.assertEquals(expNextCellBorderPosition, nextCellBorderPosition);
    }

    // Get next cell border position in RIGHT direction from cell border position;
    @Test
    void next_fromRightDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        CellBorderPosition nextCellBorderPosition = cellBorderPosition.next(Direction.RIGHT);

        CellBorderPosition expNextCellBorderPosition = new CellBorderPosition(new CellPosition(3, 2), Direction.UP, Direction.LEFT);

        Assertions.assertEquals(expNextCellBorderPosition, nextCellBorderPosition);
    }

    // Get next cell border position in LEFT direction from cell border position;
    @Test
    void next_fromLeftDirection() {
        CellBorderPosition cellBorderPosition = new CellBorderPosition(new CellPosition(2, 2), Direction.UP, Direction.LEFT);
        CellBorderPosition nextCellBorderPosition = cellBorderPosition.next(Direction.LEFT);

        CellBorderPosition expNextCellBorderPosition = new CellBorderPosition(new CellPosition(1, 2), Direction.UP, Direction.LEFT);

        Assertions.assertEquals(expNextCellBorderPosition, nextCellBorderPosition);
    }
}
