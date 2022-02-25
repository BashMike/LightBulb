package model.Navigation;


public class CellBorderPosition {
    // ============= ATTRIBUTES =============
    CellPosition 	_cellPosition;			// cell position in which between position is set
    Direction 		_verticalDirection;		// vertical direction in which position is set
    Direction 		_horizontalDirection;	// horizontal direction in which position is set

    // ============= OPERATIONS =============
    // ---------- create ----------
    /** Basic constructor (by default position is normalized to form [ CellPos; {Direction.UP, Direction.LEFT} ])
     */
    public CellBorderPosition(CellPosition cellPosition, Direction firstDirection, Direction secondDirection) {
        // Set error if two given directions has the same orientation
        if (!this.isValid(cellPosition, firstDirection, secondDirection)) {
            throw new RuntimeException("ERROR: Given direction must have different orientation.");
        }

        // Set cell position
        this._cellPosition 	= cellPosition;

        // Set vertical and horizontal directions of the position
        this._verticalDirection 	= (firstDirection.orientation() == Orientation.VERTICAL ? firstDirection : secondDirection);
        this._horizontalDirection 	= (secondDirection.orientation() == Orientation.HORIZONTAL ? secondDirection : firstDirection);

        // Normalize to form [ CellPos; {Direction.UP, Direction.LEFT} ]
        this._normalize();
    }

    /** Check if given parameters of cell border position are valid
     * @param cellPosition			cell position of cell border
     * @param firstDirection		first direction of cell border
     * @param secondDirection		second direction of cell border
     * @return sign indicating that given parameters of cell border position are valid
     */
    public boolean isValid(CellPosition cellPosition, Direction firstDirection, Direction secondDirection) {
        boolean result;
        result = (firstDirection.orientation() != secondDirection.orientation());

        return result;
    }

    /** Normalize cell border position to form [ CellPos; {Direction.UP, Direction.LEFT} ]
     */
    private void _normalize() {
        if (this._verticalDirection == Direction.DOWN) {
            this._cellPosition = this._cellPosition.next(Direction.DOWN);
            this._verticalDirection = Direction.UP;
        }

        if (this._horizontalDirection == Direction.RIGHT) {
            this._cellPosition = this._cellPosition.next(Direction.RIGHT);
            this._horizontalDirection = Direction.LEFT;
        }
    }

    /** Get copy of object
     * @return copy of object
     */
    @Override
    public CellBorderPosition clone() {
        return new CellBorderPosition(this._cellPosition, this._verticalDirection, this._horizontalDirection);
    }

    // -------- attributes --------
    /** Get cell position
     * @return cell position of the between cells position
     */
    public CellPosition cellPosition() { return this._cellPosition; }

    /** Get vertical direction
     * @return vertical direction of the cell border position
     */
    public Direction verticalDirection() { return this._verticalDirection; }

    /** Get horizontal direction
     * @return horizontal direction of the cell border position
     */
    public Direction horizontalDirection() { return this._horizontalDirection; }

    // --------- contract ---------
    /** Get next cell border position in given direction
     * @param direction - direction in which cell border position will be gotten
     * @return next cell border position in given direction
     */
    public CellBorderPosition next(Direction direction) {
        CellPosition nextCellPos = this._cellPosition.next(direction);
        CellBorderPosition result = new CellBorderPosition(nextCellPos, this._verticalDirection, this._horizontalDirection);

        return result;
    }

    /** Get between cells position in given direction
     * @param direction - direction in which between cells position will be taken
     * @return between cells position placed in given direction
     */
    public BetweenCellsPosition betweenCellsPosition(Direction direction) {
        BetweenCellsPosition result;

        switch(direction) {
            case UP:	result = new BetweenCellsPosition(this._cellPosition.next(Direction.UP), Direction.LEFT); break;
            case DOWN:	result = new BetweenCellsPosition(this._cellPosition, Direction.LEFT); break;
            case RIGHT:	result = new BetweenCellsPosition(this._cellPosition, Direction.UP); break;
            default:	result = new BetweenCellsPosition(this._cellPosition.next(Direction.LEFT), Direction.UP); break;
        }

        return result;
    }

    // -------- comparison --------
    /** Check on equality with other cell border position
     * @param other - other cell border position
     * @return sign of equality
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof CellBorderPosition) {
            CellBorderPosition otherPos = (CellBorderPosition)other;

            boolean isEqualToPos = this._cellPosition.equals(otherPos._cellPosition) &&
                    this._verticalDirection.equals(otherPos._verticalDirection) &&
                    this._horizontalDirection.equals(otherPos._horizontalDirection);

            return (isEqualToPos);
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
    public int hashCode() { return this._cellPosition.hashCode() * this._verticalDirection.hashCode() * this._horizontalDirection.hashCode(); }
}
