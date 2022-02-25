package model.Events;

public interface SillyRobotEventListener extends AbstractRobotEventListener {
	// ============= OPERATIONS =============
	/** Handle event when silly robot got into the sewer
	 * @param event - event information
	 */
	void gotIntoSewer(SillyRobotEvent event);

	/** Handle event when silly robot caught the smart robot
	 * @param event - event information
	 */
	void smartRobotIsCaught(SillyRobotEvent event);

	/** Handle event when silly robot saw the smart robot in his area of view
	 * @param event - event information
	 */
	void smartRobotIsSeen(SillyRobotEvent event);

	/** Handle event when silly robot didn't see the smart robot in his area of view
	 * @param event - event information
	 */
	void smartRobotIsHiding(SillyRobotEvent event);
}
