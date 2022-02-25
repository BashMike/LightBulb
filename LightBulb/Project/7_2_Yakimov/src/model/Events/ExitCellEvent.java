package model.Events;

import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.CellPosition;

import java.util.EventObject;

public class ExitCellEvent extends EventObject {
	// ============= ATTRIBUTES =============
	private SmartRobot _smartRobot = null;

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Constructor
	 * @param source 		object on which the event initially occurred
	 */
	public ExitCellEvent(Object source) { super(source); }

	// --------- contract ---------
	/** Set smart robot which was teleported
	 * @param smartRobot 	smart robot which was teleported
	 */
	public void setSmartRobot(SmartRobot smartRobot) { this._smartRobot = smartRobot; }

	/** Get smart robot which was teleported
	 * @return smart robot which was teleported
	 */
	public SmartRobot getSmartRobot() { return this._smartRobot; }

	// -------- comparison --------
	/** Check on equality with other event
	 * @param other 		exit cell event
	 * @return sign of equality with other event
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof ExitCellEvent) {
			ExitCellEvent otherEvent = (ExitCellEvent)other;
			return (this.source == otherEvent.source && this._smartRobot == otherEvent._smartRobot);
		}
		else {
			return false;
		}
	}
}
