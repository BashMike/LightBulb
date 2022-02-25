package model.Events;

import model.GameCore.Field;

import java.util.EventObject;

public class GameEvent extends EventObject {
	// ============= ATTRIBUTES =============
    private Field _field = null;

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Constructor
	 * @param source 		object on which the event initially occurred
	 */
	public GameEvent(Object source) { super(source); }

	// --------- contract ---------
	/** Set field
	 * @param field 		field
	 */
	public void setField(Field field) { this._field = field; }

	/** Get field
	 * @return field
	 */
	public Field getField() { return this._field; }

	// -------- comparison --------
	/** Check on equality with other event
	 * @param other 		exit cell event
	 * @return sign of equality with other event
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof GameEvent) {
			GameEvent otherEvent = (GameEvent)other;
			return (this.source == otherEvent.source && this._field == otherEvent._field);
		}
		else {
			return false;
		}
	}
}
