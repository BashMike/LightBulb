package model.Navigation;

import org.jetbrains.annotations.NotNull;

public class CellPosition {
	// ============= ATTRIBUTES =============
	private int _x; 		// x of the cell position
	private int _y; 		// y of the cell position

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public CellPosition(int x, int y) {
		this._x = x;
		this._y = y;
	}

	// -------- attributes --------
	// ~~~ coordinates ~~~
	/** Get x of the cell position
	 * @return x of the cell position
	 */
	public int x() {
		return this._x;
	}

	/** Get y of the cell position
	 * @return y of the cell position
	 */
	public int y() {
		return this._y;
	}

	// --------- contract ---------
	/** Get next cell position
	 * @param direction - cell position in given direction from current cell position
	 * @return next cell position
	 */
	public CellPosition next(@NotNull Direction direction) {
		CellPosition result;
		switch(direction) {
			case UP: 	result = new CellPosition(this.x(), this.y()-1); break;
			case RIGHT: result = new CellPosition(this.x()+1, this.y()); break;
			case DOWN: 	result = new CellPosition(this.x(), this.y()+1); break;
			default:	result = new CellPosition(this.x()-1, this.y()); break;
		}

		return result;
	}

	// -------- comparison --------
	/** Check on equality with other cell position
	 * @param other - other cell position
	 * @return sign of equality
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof CellPosition) {
			CellPosition otherCellPos = (CellPosition)other;
			return (this._x == otherCellPos._x && this._y == otherCellPos._y);
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
	public int hashCode() { return this._x * this._y; }
}
