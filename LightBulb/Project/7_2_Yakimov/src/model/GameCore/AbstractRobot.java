package model.GameCore;

import model.Events.AbstractRobotEvent;
import model.Events.AbstractRobotEventListener;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class AbstractRobot extends GameElement {
	// =========== INNER CLASSES ============
    // State of robot
	protected enum RobotState {
		SELF_CONTROL,
		SKIPPING_MOVES,
		FORCED_MOVE
	}

	// ========== STATIC ATTRIBUTES =========
	static protected int WAIT_TIME_MS = 500;

	// ============= OPERATIONS =============
	// ~~~ main ~~~
    protected int _moveSkipsCount 			= 0; // Count of skipping moves
	protected int _forceMadeMovesCount 		= 0; // Count of forced made moves

	// ~~~ event listeners ~~~
	protected ArrayList<AbstractRobotEventListener> _listeners = new ArrayList<>(); // robot's event listeners

	// ~~~ timers ~~~
    protected Timer _actionTimer = null;

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public AbstractRobot() {}

	// -------- attributes --------
	/** Get current state of the robot
	 * @return state of the robot
	 */
	@NotNull
	protected RobotState state() {
		RobotState result = null;

		if (this._moveSkipsCount == 0 && this._forceMadeMovesCount == 0) 		{ result = RobotState.SELF_CONTROL; }
		else if (this._moveSkipsCount == 0 && this._forceMadeMovesCount != 0) 	{ result = RobotState.FORCED_MOVE; }
		else if (this._moveSkipsCount != 0 && this._forceMadeMovesCount == 0) 	{ result = RobotState.SKIPPING_MOVES; }

		return result;
	}

	// --------- contract ---------
    // ~~~ making step ~~~
	/** Make a step in given direction
	 * @param direction - direction in which robot will make a step
	 * @return sign of successful making a step
	 */
    final protected boolean _makeStep(Direction direction) {
        if (!this.hasPosition()) {
        	throw new RuntimeException("ERROR: Try to move robot WITHOUT POSITION.");
		}

        // Make landscape at current position perform an action on self due to the robot movement on the landscape
		this._getAffectedByLandscapeAtCurrentPosition(true, direction);

		// Make a step if robot can make a step in given direction and robot controls himself
        boolean success = false;
        if (this.state() == RobotState.SELF_CONTROL) {
			success = this._simpleMakeStep(direction);
		}

		// Make landscape at new position perform an action on self due to the robot placement on the landscape
		if (this.state() == RobotState.SELF_CONTROL) {
		    this._getAffectedByLandscapeAtCurrentPosition(false, null);
		}

		return success;
	}

	/** Simple making a step in given direction without being affected of landscapes' actions
	 * @param direction		direction in which robot will make a step
	 * @return sign of successful making a step
	 */
	private boolean _simpleMakeStep(@NotNull Direction direction) {
		boolean success = this.canMakeStep(this._position, direction);

		// If robot can make a step in given direction
		if (success) {
			// Remove self to the cell in the given direction
			AbstractCell neighbour = this._position.neighbour(direction);

			this._position.removeGameElement(this);
			success = neighbour.placeGameElement(this);
		}

		return success;
	}

	/** Extract landscape at current position of robot and be affected by it
	 * @param isMovingAffect		sign indicating that affect must be when robot moves
	 * @param movementDirection   	direction of robot's movement
	 * @return sign of successful affect by landscape at current position of robot
	 */
	private boolean _getAffectedByLandscapeAtCurrentPosition(boolean isMovingAffect, Direction movementDirection) {
	    boolean success = false;

	    if (this.hasPosition()) {
			ArrayList<GameElement> landscapes = this._position.getGameElements(Landscape.class);
			success = !landscapes.isEmpty();

			if (success) {
				if (isMovingAffect) {
					((Landscape)landscapes.get(0)).performActionOnMovingRobot(this, movementDirection);
				}
				else {
					this.stopBeingAffectedByLandscape();
					((Landscape)landscapes.get(0)).performActionOnSteppingRobot(this);
				}
			}
		}

	    return success;
	}

	/** Check if robot can make a step in given direction
	 * @param cell 			cell from which robot will check possibility to make a step
	 * @param direction 	direction in which robot will make a step
	 * @return sign of successful possibility of making a step
	 */
	public final boolean canMakeStep(@NotNull AbstractCell cell, @NotNull Direction direction) {
	    boolean success;
	    success = !cell.hasWall(direction);
	    success &= cell.hasNeighbour(direction);

		if (success) {
			AbstractCell neighbour = cell.neighbour(direction);
			success = neighbour.canPlace(this);

			success &= this._canStepOnCellFromOther(neighbour);
			success &= this._canTakeACell(neighbour);
		}

    	return success;
	}

	/** Check if robot can step on cell from other cell
	 * @param cell		cell on which robot will step
	 * @return sign of successful possibility of making step on given cell by robot
	 */
	protected abstract boolean _canStepOnCellFromOther(@NotNull AbstractCell cell);

	// ~~~ landscapes ~~~
	/** Skip moves
	 * @param moveSkipsCount    count of moves which will be skipped
	 */
    public abstract void skipMoves(int moveSkipsCount);

	/** Move in given direction
	 * @param direction     direction in which robot will be moved
	 * @param movesCount    count of robot's moves
	 */
	void move(@NotNull Direction direction, int movesCount) {
	    // Switch to state of forced movement
        this._moveSkipsCount = 0;
		this._forceMadeMovesCount = movesCount;

		// Make "movesCount" steps
		ActionListener actionListener = e -> {
		    // If robot is moving
			if (_forceMadeMovesCount > 0) {
				// Make robot move in given direction
				boolean isMoving = _simpleMakeStep(direction);
				// Get affection of landscape on new position if robot has moved
				isMoving = isMoving && _getAffectedByLandscapeAtCurrentPosition(false, null);

				this._fireRobotHasDoneActionByTimer();

				if (!isMoving) { this._actionTimer.stop(); }
			}
			else {
				// Stop making forced moves
				this._actionTimer.stop();
			}
		};

		this._actionTimer = new Timer(WAIT_TIME_MS, actionListener);
		this._actionTimer.setRepeats(true);
		this._actionTimer.start();
		this._actionTimer.setInitialDelay(0);
	}

	/** Stop being under affect of landscape
	 */
	void stopBeingAffectedByLandscape() {
		this._actionTimer.stop();

		this._moveSkipsCount 		= 0;
		this._forceMadeMovesCount 	= 0;
	}

	// ---------- events ----------
	/** Add robot event listener
	 * @param listener 		event listener
	 * @return sign of successful adding
	 */
	public boolean addEventListener(AbstractRobotEventListener listener) { return this._listeners.add(listener); }

	/** Remove robot event listener
	 * @param listener 		event listener
	 * @return sign of successful removing
	 */
	public boolean removeEventListener(AbstractRobotEventListener listener) { return this._listeners.add(listener); }

	/** Fire event when smart robot has made a step on the field
	 * @param landscapeType		type of the landscape
	 * @param landscapeState	state of the landscape
	 */
	void fireRobotIsAffectedByLandscape(@NotNull Class<? extends Landscape> landscapeType, @NotNull LandscapeState landscapeState) {
		AbstractRobotEvent event = new AbstractRobotEvent(this);
		event.setLandscapeType(landscapeType);
		event.setLandscapeState(landscapeState);

		for (var listener : this._listeners) {
			listener.robotIsAffectedByLandscape(event);
		}
	}

	/** Fire event when robot has being waited when skipping move or moving forced
	 */
	protected void _fireRobotHasDoneActionByTimer() {
		AbstractRobotEvent event = new AbstractRobotEvent(this);

		for (var listener : this._listeners) {
			listener.robotHasDoneActionByTimer(event);
		}
	}
}