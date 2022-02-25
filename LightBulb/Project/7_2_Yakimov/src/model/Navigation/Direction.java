package model.Navigation;

import org.jetbrains.annotations.NotNull;

public enum Direction {
	// =============== VALUES ===============
	UP,
	DOWN,
	RIGHT,
	LEFT;

	// ============= ATTRIBUTES =============
	public Orientation orientation() {
		Orientation result;

		if (this == UP || this == DOWN) { result = Orientation.VERTICAL; }
		else 							{ result = Orientation.HORIZONTAL; }

		return result;
	}

	// ============= OPERATIONS =============
	// --------- contract ---------
	/** Check if given direction is opposite
	 * @param direction - direction which will be check on opposite
	 * @return sign of opposite of directions
	 */
	public boolean isOpposite(@NotNull Direction direction) {
		return this == direction.opposite();
	}

	/** Get opposite direction
	 * @return opposite direction
	 */
	public Direction opposite() {
		Direction result;

		switch(this) {
			case UP:	result = Direction.DOWN; break;
			case RIGHT:	result = Direction.LEFT; break;
			case DOWN:	result = Direction.UP; break;
			default:	result = Direction.RIGHT; break;
		}

		return result;
	}

	/** Get direction moved clockwise
	 * @return direction moved clockwise
	 */
	public Direction clockwise() {
		Direction result;

		switch(this) {
			case UP:	result = Direction.RIGHT; break;
			case RIGHT:	result = Direction.DOWN; break;
			case DOWN:	result = Direction.LEFT; break;
			default:	result = Direction.UP; break;
		}

		return result;
	}

	/** Get direction moved anticlockwise
	 * @return direction moved anticlockwise
	 */
	public Direction anticlockwise() {
		Direction result;

		switch(this) {
			case UP:	result = Direction.LEFT; break;
			case RIGHT:	result = Direction.UP; break;
			case DOWN:	result = Direction.RIGHT; break;
			default:	result = Direction.DOWN; break;
		}

		return result;
	}
}
