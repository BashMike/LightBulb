package model.GameCore.FinishingGameCore;

import model.Events.SillyRobotEvent;
import model.Events.SillyRobotEventListener;
import model.GameCore.AbstractCell;
import model.GameCore.Field;
import model.GameCore.GameElement;
import model.GameCore.AbstractRobot;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleArea;
import model.Navigation.RectangleSize;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class SillyRobot extends AbstractRobot {
	// ============= ATTRIBUTES =============
	// ~~~ main ~~~
	private Field 		_field 					= null;														// Field

	private RectangleArea _viewArea 			= new RectangleArea(new CellPosition(0, 0),			// Size of view area
																	new RectangleSize(5, 5));
	private SmartRobot 	_caughtSmartRobot		= null;														// Caught smart robot

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
     * @param field - field's info needed for finding smart robot in it
	 */
	public SillyRobot(@NotNull Field field) { this._field = field; }

	// -------- attributes --------
	// ~~~ position ~~~
	/** Check if silly robot can set position
	 * @param cell		cell which will be checked on the possibility of taking the position by silly robot
	 * @return sign of successful possibility of taking the position by silly robot
	 */
	@Override
	protected boolean _canSetPosition(@NotNull AbstractCell cell) {
		boolean success = this._field.hasCell(cell);
		success &= !(cell instanceof ExitCell);

		return success;
	}

	/** Check that sewer can be in the same cell with given game element type
	 * @param gameElementType - type of other game element
	 * @return sign of possibility being in the same with given game element type
	 */
	@Override
	public boolean canBeInSameCellWith(@NotNull Class gameElementType) {
		return true;
	}

	// --------- contract ---------
	// ~~~ movement ~~~
	/** Make a step
	 * @return sign of movement's success
	 */
	public boolean makeStep() {
	    boolean success;

	    // Skip current move if silly robot have to skip moves
	    if (this._moveSkipsCount > 0) {
	    	this._moveSkipsCount--;
	    	return false;
		}

		// Check that smart robot in personal view area
        if (this.hasPosition()) { this._updateViewArea(); } // Update view area position based on current silly robot's position

		boolean isSmartRobotInsideViewArea = this._isSmartRobotInViewArea();

		// If smart robot in personal view area
		if (isSmartRobotInsideViewArea) { success = this._smartMakeStep(); } // Make a step in the smart way
		else 							{ success = this._sillyMakeStep(); } // Make a step in the silly way

		return success;
	}

	/** Check if robot can step on cell from other cell
	 * @param cell		cell on which robot will step
	 * @return sign of successful possibility of making step on given cell by robot
	 */
	protected boolean _canStepOnCellFromOther(@NotNull AbstractCell cell) { return true; }

	/** Update silly robot's view area
	 */
	private void _updateViewArea() {
	    CellPosition cellPosition = this._field.cellPosition(this._position);
		int newViewAreaX = cellPosition.x() - this._viewArea.size().width() / 2;
		int newViewAreaY = cellPosition.y() - this._viewArea.size().height() / 2;

		this._viewArea = new RectangleArea(new CellPosition(newViewAreaX, newViewAreaY), this._viewArea.size());
	}

	/** Check that smart robot in silly robot's view area
	 * @return sign of smart robot's placement in silly robot's view area
	 */
	private boolean _isSmartRobotInViewArea() {
	    boolean success = false;

		// Get cells in personal view area from field
		Map<CellPosition, AbstractCell> viewAreaCells = this._field.cells(this._viewArea);

		// Check that smart robot is in view area's cells
		for (var viewAreaCell : viewAreaCells.entrySet()) {
			ArrayList<GameElement> smartRobotsOfCell = viewAreaCell.getValue().getGameElements(SmartRobot.class);

			success = smartRobotsOfCell.contains(this._field.getSmartRobot());
			if (success) { break; }
		}

		return success;
	}

	/** Make a step in the silly way
	 * @return sign of successfully making a step
	 */
	private boolean _sillyMakeStep() {
		boolean success = false;

		// Fire event that smart robot is hiding
		this.fireSmartRobotIsHiding();

		// Get positions of silly robot and smart robot if smart is on the field
		if (this._field.getSmartRobot().hasPosition()) {
			AbstractCell smartRobotPos = this._field.getSmartRobot().position();
			CellPosition smartRobotCellPos = this._field.cellPosition(smartRobotPos);

			CellPosition sillyRobotCellPos = this._field.cellPosition(this._position);

			// Move in direction to smart robot depending on gotten positions
			if (sillyRobotCellPos.x() < smartRobotCellPos.x()) {
				success = this._makeStep(Direction.RIGHT);
			} else if (sillyRobotCellPos.x() > smartRobotCellPos.x()) {
				success = this._makeStep(Direction.LEFT);
			}
		}

		return success;
	}

	/** Make a step in the smart way
	 * @return sign of successfully making a step
	 */
	private boolean _smartMakeStep() {
		boolean success;

		// Fire event that smart robot was seen
		this.fireSmartRobotIsSeen();

		// Get path to the smart robot
		AbstractCell smartRobotPos = this._field.getSmartRobot().position();

		ArrayList<AbstractCell> pathToSmartRobot = this._field.findShortestRoute(this, this._position, smartRobotPos);
		success = (pathToSmartRobot.size() > 1);

		// Make a step in the given path if path was successfully found
		if (success) {
			Direction direction = this._position.getDirectionOfNeighbourPlacement(pathToSmartRobot.get(1));
			super._makeStep(direction);
		}

		return success;
	}

	// ~~~ landscapes ~~~
	/** Skip moves
	 * @param moveSkipsCount    count of moves which will be skipped
	 */
	@Override
	public void skipMoves(int moveSkipsCount) {
		this._moveSkipsCount = moveSkipsCount;
	}

	// ~~~ smart robot ~~~
	/** Try to catch smart robot
	 * @return sign of successful caught
	 */
	public boolean catchSmartRobot() {
		boolean success = false;

		if (this._position.containsGameElementType(SmartRobot.class)) {
			SmartRobot smartRobot = this._field.getSmartRobot();

			success = !smartRobot.isCaught();
			if (success) {
				smartRobot.position().removeGameElement(smartRobot);
				smartRobot.becomeCaught();

				this._caughtSmartRobot = smartRobot;

				fireSmartRobotIsCaught();
			}
		}

		return success;
	}

	// ---------- events ----------
	/** Fire event when silly robot got into the sewer
	 */
	public void fireGotIntoSewer() {
		SillyRobotEvent event = new SillyRobotEvent(this);

		for (var listener : this._listeners) {
			((SillyRobotEventListener)listener).gotIntoSewer(event);
		}
	}

	/** Fire event when silly robot caught the smart robot
	 */
	public void fireSmartRobotIsCaught() {
		SillyRobotEvent event = new SillyRobotEvent(this);

		for (var listener : this._listeners) {
			((SillyRobotEventListener)listener).smartRobotIsCaught(event);
		}
	}

	/** Fire event when silly robot saw the smart robot in his area of view
	 */
	public void fireSmartRobotIsSeen() {
		SillyRobotEvent event = new SillyRobotEvent(this);

		for (var listener : this._listeners) {
			((SillyRobotEventListener)listener).smartRobotIsSeen(event);
		}
	}

	/** Fire event when silly robot didn't see the smart robot in his area of view
	 */
	public void fireSmartRobotIsHiding() {
		SillyRobotEvent event = new SillyRobotEvent(this);

		for (var listener : this._listeners) {
			((SillyRobotEventListener)listener).smartRobotIsHiding(event);
		}
	}
}
