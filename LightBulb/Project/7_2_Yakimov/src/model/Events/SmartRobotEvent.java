package model.Events;

public class SmartRobotEvent extends AbstractRobotEvent {
	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Constructor
	 * @param source 		object on which the event initially occurred
	 */
	public SmartRobotEvent(Object source) { super(source); }

	// -------- comparison --------
	/** Check on equality with other event
	 * @param other 		smart robot event
	 * @return sign of equality with other event
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof SmartRobotEvent) {
			return super.equals(other);
		}
		else {
			return false;
		}
	}
}
