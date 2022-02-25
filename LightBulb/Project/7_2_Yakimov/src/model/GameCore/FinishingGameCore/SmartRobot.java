package model.GameCore.FinishingGameCore;

import model.Events.SmartRobotEvent;
import model.Events.SmartRobotEventListener;
import model.GameCore.AbstractCell;
import model.GameCore.AbstractRobot;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SmartRobot extends AbstractRobot {
	// ============= ATTRIBUTES =============
	// ~~~ main ~~~
	private boolean _isCaught 		= false;	// Sign of being caught by exit cell or silly robot
	private boolean _isDestroyed 	= false;	// Sign of being destroyed

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public SmartRobot() {}

	// -------- attributes --------
	// ~~~ caught by somebody ~~~
	/** Get sign of being caught by exit cell or silly robot
	 * @return sign of being caught by somebody
	 */
	public boolean isCaught() { return this._isCaught; }

	/** Become caught by somebody
	 * @return sign of successful becoming caught
	 */
	boolean becomeCaught() {
		boolean success = !this.hasPosition() && !this.isCaught() && !this.isDestroyed();
		if (success) { this._isCaught = true; }

		return success;
	}

	/** Become uncaught
	 * @return sign of successful becoming uncaught
	 */
	boolean becomeUncaught() {
		boolean success = this.isCaught();
		if (success) { this._isCaught = false; }

		return success;
	}

	// ~~~ destroy ~~~
	/** Get sign of being destroyed
	 * @return sign of being destroyed
	 */
    public boolean isDestroyed() { return this._isDestroyed; }

	/** Destroy self
	 */
	void destroySelf() {
		this._isDestroyed = true;
		this.fireSmartRobotHasBeenDestroyed();
	}

	// ~~~ position ~~~
	/** Check if smart robot can set position
	 * @param cell		cell which will be checked on the possibility of taking the position by smart robot
	 * @return sign of successful possibility of taking the position by smart robot
	 */
	@Override
	protected boolean _canSetPosition(@NotNull AbstractCell cell) { return !this.isCaught() && !this.isDestroyed(); }

	/** Check that smart robot can be in the same cell with given game element type
	 * @param gameElementType - type of other game element
	 * @return sign of possibility being in the same with given game element type
	 */
	@Override
	public boolean canBeInSameCellWith(@NotNull Class gameElementType) { return (gameElementType != Sewer.class); }

	// --------- contract ---------
	// ~~~ movement ~~~
	/** Make a step in given direction
	 * @param direction  - direction in which robot will go
	 * @return sign of movement's success
	 */
	public boolean makeStep(Direction direction) {
		boolean success = (!this.isCaught() && !this.isDestroyed());
		if (success) {
			success &= this._makeStep(direction);
			this.fireSmartRobotHasMadeAStep();
		}

		return success;
	}

	/** Check if robot can step on cell from other cell
	 * @param cell		cell on which robot will step
	 * @return sign of successful possibility of making step on given cell by robot
	 */
	protected boolean _canStepOnCellFromOther(@NotNull AbstractCell cell) {
		boolean success = !cell.containsGameElementType(Sewer.class);
		return success;
	}

	// ~~~ landscapes ~~~
	/** Skip moves
	 * @param moveSkipsCount    count of moves which will be skipped
	 */
	@Override
	public void skipMoves(int moveSkipsCount) {
		if (this.isCaught() || this.isDestroyed()) {
			return;
		}

		// Switch to state of skipping moves
		this._moveSkipsCount = moveSkipsCount;
		this._forceMadeMovesCount = 0;

		// Skip "skipMovesCount" steps
		ActionListener actionListener = e -> {
			// If robot is skipping moves
			if (moveSkipsCount > 0) {
				fireSmartRobotHasSkippedMove();
			}
			else {
				// Stop skipping steps
				this._actionTimer.stop();
			}
		};

		this._actionTimer = new Timer(WAIT_TIME_MS, actionListener);
		this._actionTimer.setRepeats(true);
		this._actionTimer.start();
	}

	// ---------- events ----------
	/** Fire event when smart robot has made a step on the field
	 */
	public void fireSmartRobotHasMadeAStep() {
		SmartRobotEvent event = new SmartRobotEvent(this);

		for (var listener : this._listeners) {
			((SmartRobotEventListener)listener).smartRobotHasMadeAStep(event);
		}
	}

	/** Fire event when smart robot has skipped current move
	 */
	public void fireSmartRobotHasSkippedMove() {
		SmartRobotEvent event = new SmartRobotEvent(this);

		for (var listener : this._listeners) {
			((SmartRobotEventListener)listener).smartRobotHasSkippedMove(event);
		}
	}

	/** Fire event when smart robot has been destroyed
	 */
	public void fireSmartRobotHasBeenDestroyed() {
		SmartRobotEvent event = new SmartRobotEvent(this);

		for (var listener : this._listeners) {
			((SmartRobotEventListener)listener).smartRobotHasBeenDestroyed(event);
		}
	}
}