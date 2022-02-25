package model.Events;

public interface SmartRobotEventListener extends AbstractRobotEventListener {
	// ============= OPERATIONS =============
	/** Handle event when smart robot has made a step on the field
	 * @param event 		event information
	 */
	void smartRobotHasMadeAStep(SmartRobotEvent event);

	/** Handle event when smart robot has skipped current move
	 * @param event 		event information
	 */
	void smartRobotHasSkippedMove(SmartRobotEvent event);

	/** Handle event when smart robot has been destroyed
	 * @param event 		event information
	 */
	void smartRobotHasBeenDestroyed(SmartRobotEvent event);
}
