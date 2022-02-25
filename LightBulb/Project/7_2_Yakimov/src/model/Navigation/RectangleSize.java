package model.Navigation;

public class RectangleSize {
	// ============= ATTRIBUTES =============
	private int _width;		// width of the field
	private int _height;	// height of the field

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Check if rectangle size is valid
	 * @param width 	- width of rectangle size
	 * @param height 	- height of rectangle size
	 * @return sign of valid rectangle size's parameters
	 */
	private boolean _isValid(int width, int height) {
		boolean success = (width > 0 && height > 0);
		return success;
	}

	/** Basic constructor
	 * @param width 	- width of rectangle size
	 * @param height 	- height of rectangle size
	 */
	public RectangleSize(int width, int height) {
		if (!this._isValid(width, height)) {
			throw new RuntimeException("ERROR: Try to create INVALID rectangle size.");
		}

		this._width = width;
		this._height = height;
	}

	// -------- attributes --------
	// ~~~ sizes ~~~
	/** Get width of the field
	 * @return width of the field
	 */
	public int width() {
		return this._width;
	}

	/** Get height of the field
	 * @return height of the field
	 */
	public int height() {
		return this._height;
	}

	// -------- comparison --------
	/** Check on equality with other rectangle size
	 * @param other - other rectangle size
	 * @return sign of equality
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof RectangleSize) {
			RectangleSize otherRectSize = (RectangleSize)other;
			return (this._width == otherRectSize._width && this._height == otherRectSize._height);
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
	public int hashCode() { return this._width * this._height; }
}
