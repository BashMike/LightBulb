package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_Direction {
	// ============ test part ============
	// --- tests short description ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
    //
    // 2. Get new directions;
	// 2.1. Get opposite direction;
	// 2.1.1. Get opposite of up direction;
	// 2.1.2. Get opposite of down direction;
	// 2.1.3. Get opposite of right direction;
	// 2.1.4. Get opposite of left direction.
	// 2.2. Get clockwise direction;
	// 2.2.1. Get opposite of up direction;
	// 2.2.2. Get opposite of down direction;
	// 2.2.3. Get opposite of right direction;
	// 2.2.4. Get opposite of left direction.
	// 2.3. Get anticlockwise direction.
	// 2.3.1. Get opposite of up direction;
	// 2.3.2. Get opposite of down direction;
	// 2.3.3. Get opposite of right direction;
	// 2.3.4. Get opposite of left direction.
    //
	// 3. Orientation.
	// 3.1. Check orientation of direction.
	// 3.1.1. Get opposite of up direction;
	// 3.1.2. Get opposite of down direction;
	// 3.1.3. Get opposite of right direction;
	// 3.1.4. Get opposite of left direction.

	// ========= CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicCreating_simpleCreation() {
		Direction direction = Direction.UP;
		Assertions.assertTrue(direction == Direction.UP);
	}

	// ===== GET NEW DIRECTIONS =====
	// --- GET OPPOSITE DIRECTION ---
	// Get opposite of UP direction
	@Test
	void opposite_oppositeOfUp() {
		Direction direction = Direction.UP;
		Direction opposite = direction.opposite();

		Assertions.assertTrue(opposite == Direction.DOWN && opposite.isOpposite(direction));
	}

	// Get opposite of DOWN direction
	@Test
	void opposite_oppositeOfDown() {
		Direction direction = Direction.DOWN;
		Direction opposite = direction.opposite();

		Assertions.assertTrue(opposite == Direction.UP && opposite.isOpposite(direction));
	}

	// Get opposite of RIGHT direction
	@Test
	void opposite_oppositeOfRight() {
		Direction direction = Direction.RIGHT;
		Direction opposite = direction.opposite();

		Assertions.assertTrue(opposite == Direction.LEFT && opposite.isOpposite(direction));
	}

	// Get opposite of LEFT direction
	@Test
	void opposite_oppositeOfLeft() {
		Direction direction = Direction.LEFT;
		Direction opposite = direction.opposite();

		Assertions.assertTrue(opposite == Direction.RIGHT && opposite.isOpposite(direction));
	}

	// --- GET CLOCKWISE DIRECTION ---
	// Get clockwise direction of UP direction
	@Test
	void clockwise_upDirection() {
		Direction direction = Direction.UP;
		Direction clockwise = direction.clockwise();

		Assertions.assertTrue(clockwise == Direction.RIGHT);
	}

	// Get clockwise direction of DOWN direction
	@Test
	void clockwise_rightDirection() {
		Direction direction = Direction.RIGHT;
		Direction clockwise = direction.clockwise();

		Assertions.assertTrue(clockwise == Direction.DOWN);
	}

	// Get clockwise direction of RIGHT direction
	@Test
	void clockwise_downDirection() {
		Direction direction = Direction.DOWN;
		Direction clockwise = direction.clockwise();

		Assertions.assertTrue(clockwise == Direction.LEFT);
	}

	// Get clockwise direction of LEFT direction
	@Test
	void clockwise_leftDirection() {
		Direction direction = Direction.LEFT;
		Direction clockwise = direction.clockwise();

		Assertions.assertTrue(clockwise == Direction.UP);
	}

	// --- GET ANTICLOCKWISE DIRECTION ---
	// Get anticlockwise direction of UP direction
	@Test
	void anticlockwise_upDirection() {
		Direction direction = Direction.UP;
		Direction anticlockwise = direction.anticlockwise();

		Assertions.assertTrue(anticlockwise == Direction.LEFT);
	}

	// Get anticlockwise direction of DOWN direction
	@Test
	void anticlockwise_rightDirection() {
		Direction direction = Direction.RIGHT;
		Direction anticlockwise = direction.anticlockwise();

		Assertions.assertTrue(anticlockwise == Direction.UP);
	}

	// Get anticlockwise direction of RIGHT direction
	@Test
	void anticlockwise_downDirection() {
		Direction direction = Direction.DOWN;
		Direction anticlockwise = direction.anticlockwise();

		Assertions.assertTrue(anticlockwise == Direction.RIGHT);
	}

	// Get anticlockwise direction of LEFT direction
	@Test
	void anticlockwise_leftDirection() {
		Direction direction = Direction.LEFT;
		Direction anticlockwise = direction.anticlockwise();

		Assertions.assertTrue(anticlockwise == Direction.DOWN);
	}

	// ======== ORIENTATION ========
	// --- CHECK ORIENTATION OF DIRECTION ---
	// Get orientation of UP direction
	@Test
	void orientation_upDirection() {
		Direction direction = Direction.UP;
		Assertions.assertTrue(direction.orientation() == Orientation.VERTICAL);
	}

	// Get orientation of DOWN direction
	@Test
	void orientation_downDirection() {
		Direction direction = Direction.DOWN;
		Assertions.assertTrue(direction.orientation() == Orientation.VERTICAL);
	}

	// Get orientation of RIGHT direction
	@Test
	void orientation_rightDirection() {
		Direction direction = Direction.RIGHT;
		Assertions.assertTrue(direction.orientation() == Orientation.HORIZONTAL);
	}

	// Get orientation of LEFT direction
	@Test
	void orientation_leftDirection() {
		Direction direction = Direction.LEFT;
		Assertions.assertTrue(direction.orientation() == Orientation.HORIZONTAL);
	}
}
