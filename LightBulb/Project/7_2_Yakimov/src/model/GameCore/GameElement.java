package model.GameCore;

import org.jetbrains.annotations.NotNull;

public abstract class GameElement {
	// ============= ATTRIBUTES =============
	protected AbstractCell 	_position = null;		// position of the game element

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public GameElement() {}

	// -------- attributes --------
	// ~~~ position ~~~
	/** Check if game element has a position
	 * @return sign of game element having a position
	 */
	public boolean hasPosition() { return (this._position != null); }

	/** Get game element's position
	 * @return game element's position
	 */
	public AbstractCell position() 	{
		if (!this.hasPosition()) {
			throw new RuntimeException("ERROR: try to get NON-EXISTENT position.");
		}

		return this._position;
	}

	/** Set game element's position
	 * @param cell - set position
	 * @return sign of successful setting position
	 */
	boolean setPosition(AbstractCell cell) {
		boolean success;

		if (!this.hasPosition() && this._canTakeACell(cell)) {
			this._position = cell;
			success = true;
		}
		else {
			success = false;
		}

		return success;
	}

	/** Unset game element's position
	 * @return sign of successful unsetting position
	 */
	boolean unsetPosition() {
		boolean success;

		if (this.hasPosition()) {
			this._position = null;
			success = true;
		}
		else {
			success = false;
		}

		return success;
	}

	/** Check if game element can take a cell
	 * @param cell - cell which will be checked for placing by game element
	 * @return sign of game element's possibility to take a cell
	 */
	final protected boolean _canTakeACell(@NotNull AbstractCell cell) {
		// Check that game element can set given cell as its position
		boolean success = this._canSetPosition(cell);

		// Check that placed game element can be in the given cell with all its inner game elements
		for (var gameElement : cell.getGameElements(GameElement.class)) {
		    // If game element is single landscape
			if (gameElement instanceof SingleLandscape) {
				// Get type of the single landscape
				success &= this.canBeInSameCellWith(((SingleLandscape)gameElement).type());
			}
			else {
			    // Get type of the game element
				success &= this.canBeInSameCellWith(gameElement.getClass());
			}
		}

        return success;
	}

	/** Check if game element can set position
	 * @param cell		cell which will be checked on the possibility of taking the position by game element
	 * @return sign of successful possibility of taking the position by game element
	 */
	protected abstract boolean _canSetPosition(@NotNull AbstractCell cell);

	/** Check that game element can be in the same cell with given game element type
	 * @param gameElementType - type of other game element
	 * @return sign of possibility being in the same with given game element type
	 */
	public abstract boolean canBeInSameCellWith(@NotNull Class gameElementType);
}
