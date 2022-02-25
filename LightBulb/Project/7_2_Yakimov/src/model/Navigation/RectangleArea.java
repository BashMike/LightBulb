package model.Navigation;

import org.jetbrains.annotations.NotNull;

public class RectangleArea {
	// ============= ATTRIBUTES =============
	private CellPosition 	_leftTopPos;	// left top cell position of the area
	private RectangleSize 	_areaSize;		// size of the area

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public RectangleArea(CellPosition leftTopPos, RectangleSize areaSize) {
		this._leftTopPos = leftTopPos;
		this._areaSize = areaSize;
	}

	// -------- attributes --------
	/** Get left top cell position of the area
	 * @return left top cell position of the area
	 */
	public CellPosition leftTopPosition() {
		return this._leftTopPos;
	}

	/** Get area's size
	 * @return area's size
	 */
	public RectangleSize size() {
		return this._areaSize;
	}

	// --------- contract ---------
	/** Get right top cell position of the area
	 * @return right top cell position of the area
	 */
	public CellPosition rightTopPosition() {
		return new CellPosition(this._leftTopPos.x() + this._areaSize.width() - 1, this._leftTopPos.y());
	}

	/** Get left bottom cell position of the area
	 * @return left bottom cell position of the area
	 */
	public CellPosition leftBottomPosition() {
		return new CellPosition(this._leftTopPos.x(), this._leftTopPos.y() + this._areaSize.height() - 1);
	}

	/** Get right bottom cell position of the area
	 * @return right bottom cell position of the area
	 */
	public CellPosition rightBottomPosition() {
		return new CellPosition(this._leftTopPos.x() + this._areaSize.width() - 1, this._leftTopPos.y() + this._areaSize.height() - 1);
	}

    /** Check if given cell is covered by rectangle area
     * @param cellPosition		cell position
     * @return sign of having given cell position inside the rectangle area
     */
    public boolean isCovering(@NotNull CellPosition cellPosition) {
		boolean result = (cellPosition.x() >= this._leftTopPos.x());
		if (result) { result &= (cellPosition.y() >= this._leftTopPos.y()); }
		if (result) { result &= (cellPosition.x() <= this._leftTopPos.x() + this._areaSize.width() - 1); }
		if (result) { result &= (cellPosition.y() <= this._leftTopPos.y() + this._areaSize.height() - 1); }

		return result;
	}

	/** Get cell position relative to the rectangle area position (origin of coordinates is set to left top position of the rectangle area)
	 * @param cellPosition		input cell position
	 * @return cell position relative to the rectangle area position
	 */
	public CellPosition getRelativePosition(@NotNull CellPosition cellPosition) {
    	CellPosition result;

    	int newCellPositionX = cellPosition.x() - this._leftTopPos.x();
		int newCellPositionY = cellPosition.y() - this._leftTopPos.y();
		result = new CellPosition(newCellPositionX, newCellPositionY);

		return result;
	}

	/** Get new rectangle area moved to given cell position
	 * @param cellPosition		cell position to which given rectangle area will be moved
	 * @return new rectangle area moved to given cell position
	 */
	public RectangleArea moveTo(@NotNull CellPosition cellPosition) { return new RectangleArea(cellPosition, this._areaSize); }

	// -------- comparison --------
	/** Check on equality with other rectangle area
	 * @param other - other rectangle area
	 * @return sign of equality
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof RectangleArea) {
			RectangleArea otherRectArea = (RectangleArea)other;
			return (this._leftTopPos.equals(otherRectArea._leftTopPos) && this._areaSize.equals(otherRectArea._areaSize));
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
	public int hashCode() { return this._leftTopPos.hashCode() * this._areaSize.hashCode(); }
}
