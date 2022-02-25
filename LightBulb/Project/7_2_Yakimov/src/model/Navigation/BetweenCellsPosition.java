package model.Navigation;

import model.GameCore.AbstractCell;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

public class BetweenCellsPosition {
	// ============= ATTRIBUTES =============
	CellPosition 	_cellPosition;	// cell position in which between position is set
	Direction 		_direction;		// direction in which position is set

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor (by default position is normalized to form [ CellPos; {Direction.UP | Direction.LEFT} ])
	 */
	public BetweenCellsPosition(@NotNull CellPosition cellPosition, @NotNull Direction direction) {
		this._cellPosition 	= cellPosition;
		this._direction 	= direction;

		// Normalize to form [ CellPos; {Direction.UP | Direction.LEFT} ]
        this._normalize();
	}

	/** Normalize between cells position to form [ CellPos; {Direction.UP | Direction.LEFT} ]
	 */
	private void _normalize() {
		if (this._direction == Direction.RIGHT) {
			this._cellPosition = this._cellPosition.next(Direction.RIGHT);
			this._direction = Direction.LEFT;
		}
		else if (this._direction == Direction.DOWN) {
			this._cellPosition = this._cellPosition.next(Direction.DOWN);
			this._direction = Direction.UP;
		}
	}

	/** Check if between cells position is normalized
	 * @return sign of being normalized
	 */
	public boolean isNormalized() { return (this == this.normalized()); }

	/** Get normalized between cell position to form [ CellPos; {Direction.UP | Direction.LEFT} ]
	 * @return normalized between cell position
	 */
	public BetweenCellsPosition normalized() {
	    // Create new position from current state because as default new created position is normalized
		BetweenCellsPosition result = new BetweenCellsPosition(this._cellPosition, this._direction);
		return result;
	}

	/** Get denormalized between cell position to form [ CellPos; {Direction.DOWN | Direction.RIGHT} ]
	 * @return denormalized between cell position
	 */
	public BetweenCellsPosition denormalized() {
		BetweenCellsPosition result = new BetweenCellsPosition(this._cellPosition, this._direction);

		if (result._direction == Direction.LEFT) {
			result._cellPosition = result._cellPosition.next(Direction.LEFT);
			result._direction = Direction.RIGHT;
		}
		else if (result._direction == Direction.UP) {
			result._cellPosition = result._cellPosition.next(Direction.UP);
			result._direction = Direction.DOWN;
		}

		return result;
	}

	@Override
	public BetweenCellsPosition clone() {
		return new BetweenCellsPosition(this._cellPosition, this._direction);
	}

	// -------- attributes --------
	/** Get cell position
	 * @return cell position of the between cells position
	 */
	public CellPosition cellPosition() { return this._cellPosition; }

	/** Get direction
	 * @return direction of the between cells position
	 */
	public Direction direction() { return this._direction; }

	/** Get orientation (vertical, horizontal) of between cells position
	 * @return orientation (vertical, horizontal) of between cells position
	 */
	public Orientation orientation() {
		Orientation result;

		if (this._direction == Direction.UP || this._direction == Direction.DOWN) {
			result = Orientation.HORIZONTAL;
		}
		else {
			result = Orientation.VERTICAL;
		}

		return result;
	}

	// --------- contract ---------
	/** Get next between cell position in given direction
	 * @param direction - direction in which between cells position will be gotten
	 * @return next between cell position in given direction
	 */
	public BetweenCellsPosition next(@NotNull Direction direction) {
		CellPosition nextCellPos = this._cellPosition.next(direction);
		BetweenCellsPosition result = new BetweenCellsPosition(nextCellPos, this._direction);

		return result;
	}

	/** Get cell position in given direction
	 * @param direction - direction in which cell position will be taken
	 * @return cell position placed in given direction from between cells position
	 */
	@NotNull
	public CellPosition cellPosition(@NotNull Direction direction) {
		// Check if cell position can be gotten in given direction
		if (this.orientation() == direction.orientation()) {
			throw new RuntimeException("ERROR: impossible to get cell position in given direction due to the orientation of between cells position.");
		}

		// Get cell position
		CellPosition result;
		switch(direction) {
			case UP:	result = this._cellPosition.next(Direction.UP); break;
			case DOWN:
			case RIGHT:	result = this._cellPosition; break;
			default:	result = this._cellPosition.next(Direction.LEFT); break;
		}

		return result;
	}

	// -------- comparison --------
	/** Check on equality with other between cells position
	 * @param other - other between cells position
	 * @return sign of equality
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof BetweenCellsPosition) {
			BetweenCellsPosition otherPos 			= (BetweenCellsPosition)other;
			BetweenCellsPosition normalizedOtherPos = otherPos.normalized();

			boolean isEqualToPos 			= this._cellPosition.equals(otherPos._cellPosition) && this._direction.equals(otherPos._direction);
			boolean isEqualToNormalizedPos 	= this._cellPosition.equals(normalizedOtherPos._cellPosition) && this._direction.equals(normalizedOtherPos._direction);

			return (isEqualToPos || isEqualToNormalizedPos);
		}
		else {
			return false;
		}
	}

	// -------- additional --------
	/** Calculate hash code
	 * @return hash code
	 */
	@Override
	public int hashCode() { return this._cellPosition.hashCode() * this._direction.hashCode(); }
}