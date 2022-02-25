package model.Events;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
	// ============= OPERATIONS =============
	/** Handle event when game was created
	 * @param event - event information
	 */
	void gameWasCreated(GameEvent event);

	/** Handle event when game's field is changed
	 * @param event - event information
	 */
	void gameFieldIsChanged(GameEvent event);

	/** Handle event when game has defined that smart robot won
	 * @param event - event information
	 */
	void smartRobotWon(GameEvent event);

	/** Handle event when game has defined that silly robot won
	 * @param event - event information
	 */
	void sillyRobotWon(GameEvent event);
}