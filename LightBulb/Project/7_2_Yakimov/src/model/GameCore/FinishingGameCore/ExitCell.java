package model.GameCore.FinishingGameCore;

import model.Cells.StandardCell;
import model.Events.ExitCellEvent;
import model.Events.ExitCellEventListener;
import model.GameCore.AbstractCell;
import model.GameCore.GameElement;

import java.util.ArrayList;

public class ExitCell extends AbstractCell {
	// ============= ATTRIBUTES =============
	// ~~~ main ~~~
	private SmartRobot _caughtSmartRobot = null;	// caught smart robot

	// ~~~ event listeners ~~~
	private ArrayList<ExitCellEventListener> _listeners = new ArrayList<>(); // exit cell's event listeners

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public ExitCell() {}

	// --------- contract ---------
	// ~~~ game element ~~~
	/** Place game element
	 * @param gameElement   - given game element which will be placed
	 * @return success of placing game element on the cell
	 */
	public boolean placeGameElement(GameElement gameElement) {
		boolean success;

		if (gameElement instanceof SmartRobot) {
			success = this._teleportSmartRobot((SmartRobot)gameElement);
		}
		else {
			// Check if smart robot can be placed on cell with given game element
			SmartRobot smartRobot = new SmartRobot();
			boolean canSmartRobotBePlaced = smartRobot.canBeInSameCellWith(gameElement.getClass());

			// Try to place game element if smart robot can be place with given game element
			if (canSmartRobotBePlaced) 	{ success = super.placeGameElement(gameElement); }
			else 						{ success = false; }
		}

		return success;
	}

	/** Teleport smart robot in yourself
	 * @param smartRobot - smart robot
	 * @return sign of successful teleporting that smart robot
	 */
	private boolean _teleportSmartRobot(SmartRobot smartRobot) {
		boolean success = (this._caughtSmartRobot == null);
		success &= smartRobot.becomeCaught();

		if (success) {
			this._caughtSmartRobot = smartRobot;
			fireSmartRobotIsTeleported();
		}

		return success;
	}

	// ---------- events ----------
	/** Add exit cell event listener
	 * @param listener - event listener
	 * @return sign of successful adding
	 */
	public boolean addEventListener(ExitCellEventListener listener) {
		return this._listeners.add(listener);
	}

	/** Remove exit cell event listener
	 * @param listener - event listener
	 * @return sign of successful removing
	 */
	public boolean removeEventListener(ExitCellEventListener listener) {
		return this._listeners.remove(listener);
	}

	/** Fire event when smart robot is teleported to the exit cell
	 */
	public void fireSmartRobotIsTeleported() {
		ExitCellEvent event = new ExitCellEvent(this);
		event.setSmartRobot(this._caughtSmartRobot);

		for (var listener : this._listeners) {
			listener.smartRobotIsTeleported(event);
		}
	}
}
