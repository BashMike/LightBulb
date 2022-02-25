package model.Additional;

public class Pair<Key, Value> {
	// ============= ATTRIBUTES =============
	Key 	_key;		// pair's key
	Value	_value;		// pair's value

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Constructor
	 * @param key 	- key of pair
	 * @param value - value of pair
	 */
	public Pair(Key key, Value value) {
		this._key 	= key;
		this._value = value;
	}

	// -------- contract --------
	/** Get key of the pair
	 * @return key of the pair
	 */
	public Key key() { return this._key; }

	/** Get value of the pair
	 * @return value of the pair
	 */
	public Value value() { return this._value; }

	// ------- comparison -------
	/** Check on equality with other cell position
	 * @param other - other cell position
	 * @return sign of equality
	 */
	@Override
	public boolean equals(Object other) {
		boolean areKeysTypeSame = this._key.getClass() == other.getClass();
		boolean areValuesTypeSame = this._key.getClass() == other.getClass();

		if (other instanceof Pair && areKeysTypeSame && areValuesTypeSame) {
			Pair otherCellPos = (Pair)other;
			return (this._key == otherCellPos._key && this._value == otherCellPos._value);
		}
		else {
			return false;
		}
	}
}
