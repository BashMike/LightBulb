package model.Events;

public class SillyRobotEvent extends AbstractRobotEvent {
	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Constructor
	 * @param source 		object on which the event initially occurred
	 */
	public SillyRobotEvent(Object source) { super(source); }

	// -------- comparison --------
	/** Check on equality with other event
	 * @param other 		silly robot event
	 * @return sign of equality with other event
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof SillyRobotEvent) {
			return super.equals(other);
		}
		else {
			return false;
		}
	}
}
