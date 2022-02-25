package model.Events;

import java.util.EventListener;

public interface ExitCellEventListener extends EventListener {
	// ============= OPERATIONS =============
	/** Handle event when smart robot is teleported to the exit cell
	 * @param event - event information
	 */
	void smartRobotIsTeleported(ExitCellEvent event);
}
