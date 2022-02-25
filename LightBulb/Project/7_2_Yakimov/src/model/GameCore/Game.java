package model.GameCore;

import model.Events.*;
import model.MazeDesigners.SimpleMazeDesigner;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Game {
	// ============= ATTRIBUTES =============
	// ~~~ main ~~~
	private GameState 	_state 					= GameState.GAME_INIT;	// State of the game
	private Field 		_field 					= null;					// Game's field

	private Season		_season					= Season.WINTER;		// Current season
    private int 		_seasonChangeCounter 	= 0;					// Current counter value of season changing
	private int 		_seasonDuration 		= 3;					// Duration of each season in moves

	// ~~~ event listeners ~~~
	private ArrayList<GameEventListener> _listeners = new ArrayList<>(); // game's event listeners

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public Game() {}

	// --------- contract ---------
	/** Run the game
	 */
	public void run() {
		// Create field
		this._field = new Field(new SimpleMazeDesigner());

		// Subscribe needed game elements
		this._field.getSmartRobot().addEventListener(new SmartRobotEventObserver());
		this._field.getSillyRobot().addEventListener(new SillyRobotEventObserver());
		this._field.getExitCell().addEventListener(new Game.ExitCellObserver());

		// Update landscapes on the field based on initial season
		this._field.updateLandscapes(this._season);

		// Set that game is running
		this._state = GameState.GAME_RUNNING;

		// Tell listeners that game was created
		this.fireGameWasCreated();
	}

	/** Update season
	 */
	private void _updateSeason() {
		// Update current counter of season changing
		this._seasonChangeCounter++;

		// If season ends
		if (this._seasonChangeCounter == this._seasonDuration) {
			// Set that new season begins
			this._seasonChangeCounter = 0;

			// Set current season to the next season
			this._season = this._season.next();
			// Update field's landscapes
			this._field.updateLandscapes(this._season);
		}
	}

	/** Force quit the game
	 */
	public void forceQuit() {
		this._state = GameState.FORCE_QUIT;
	}

	// ---------- events ----------
	/** Add game event listener
	 * @param listener - event listener
	 * @return sign of successful adding
	 */
	public boolean addEventListener(GameEventListener listener) {
		return this._listeners.add(listener);
	}

	/** Remove game event listener
	 * @param listener - event listener
	 * @return sign of successful removing
	 */
	public boolean removeEventListener(GameEventListener listener) {
		return this._listeners.remove(listener);
	}

	/** Fire event when game was created
	 */
	public void fireGameWasCreated() {
		GameEvent gameEvent = new GameEvent(this);
		gameEvent.setField(this._field);

		for (var listener : this._listeners) {
			listener.gameWasCreated(gameEvent);
		}
	}

	/** Fire event when game's field is changed
	 */
	public void fireGameFieldIsChanged() {
		GameEvent gameEvent = new GameEvent(this);

		for (var listener : this._listeners) {
			listener.gameFieldIsChanged(gameEvent);
		}
	}

	/** Fire event when game has defined that smart robot won
	 */
	public void fireSmartRobotWon() {
		GameEvent gameEvent = new GameEvent(this);

		for (var listener : this._listeners) {
			listener.smartRobotWon(gameEvent);
		}
	}

	/** Fire event when game has defined that silly robot won
	 */
	public void fireSillyRobotWon() {
		GameEvent gameEvent = new GameEvent(this);

		for (var listener : this._listeners) {
			listener.sillyRobotWon(gameEvent);
		}
	}

	// =========== INNER CLASSES ============
	// Season of the year
	public enum Season {
		// =============== VALUES ===============
        WINTER,
		SPRING,
		SUMMER,
		AUTUMN;

		// ============= ATTRIBUTES =============
        @NotNull
		public Season next() {
			Season result = null;

			if (this == WINTER) 		{ result = SPRING; }
			else if (this == SPRING) 	{ result = SUMMER; }
			else if (this == SUMMER) 	{ result = AUTUMN; }
			else if (this == AUTUMN) 	{ result = WINTER; }

			return result;
		}
	}

	// Exit cell observer
	private class ExitCellObserver implements ExitCellEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** Handle event when smart robot is teleported to the exit cell
		 * @param event - event information
		 */
		@Override
		public void smartRobotIsTeleported(ExitCellEvent event) {
			if (_state == GameState.GAME_RUNNING) {
				_state = GameState.SMART_ROBOT_WINS;

				fireSmartRobotWon();
			}
		}
	}

	// Silly robot observer
	private class SillyRobotEventObserver implements SillyRobotEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** GAME DOESN'T HANDLE THIS EVENT
		 * Handle event when robot has being affected by landscape
		 * @param event - event information
		 */
		public void robotIsAffectedByLandscape(AbstractRobotEvent event) {}

		/** Handle event when robot has being waited when skipping move or moving forced
		 * @param event - event information
		 */
		public void robotHasDoneActionByTimer(AbstractRobotEvent event) {
			fireGameFieldIsChanged();
		}

		/** GAME DOESN'T HANDLE THIS EVENT
		 * Handle event when silly robot got into the sewer
		 * @param event - event information
		 */
		public void gotIntoSewer(SillyRobotEvent event) {}

		/** Handle event when silly robot caught the smart robot
		 * @param event - event information
		 */
		public void smartRobotIsCaught(SillyRobotEvent event) {
			if (_state == GameState.GAME_RUNNING) {
				_state = GameState.SILLY_ROBOT_WINS;

				fireSillyRobotWon();
			}
		}

		/** GAME DOESN'T HANDLE THIS EVENT
		 * Handle event when silly robot saw the smart robot in his area of view
		 * @param event - event information
		 */
		public void smartRobotIsSeen(SillyRobotEvent event) {}

		/** GAME DOESN'T HANDLE THIS EVENT
		 * Handle event when silly robot didn't see the smart robot in his area of view
		 * @param event - event information
		 */
		public void smartRobotIsHiding(SillyRobotEvent event) {}
	}

	// Smart robot observer
	private class SmartRobotEventObserver implements SmartRobotEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** GAME DOESN'T HANDLE THIS EVENT
		 * Handle event when robot has being affected by landscape
		 * @param event - event information
		 */
		public void robotIsAffectedByLandscape(AbstractRobotEvent event) {}

		/** Handle event when robot has being waited when skipping move or moving forced
		 * @param event - event information
		 */
		public void robotHasDoneActionByTimer(AbstractRobotEvent event) {
		    fireGameFieldIsChanged();
		}

		/** Handle event when smart robot has made a step on the field
		 * @param event - event information
		 */
		public void smartRobotHasMadeAStep(SmartRobotEvent event) {
			// Make silly robot try to catch smart robot if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { _field.getSillyRobot().catchSmartRobot(); }
			// Make silly robot to make a step if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { _field.getSillyRobot().makeStep(); }
			// Make silly robot try to catch smart robot if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { _field.getSillyRobot().catchSmartRobot(); }
			// Update season
			_updateSeason();

			// Set that field can be redrawn if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { fireGameFieldIsChanged(); }
		}

		/** Handle event when smart robot has skipped current move
		 * @param event - event information
		 */
		public void smartRobotHasSkippedMove(SmartRobotEvent event) {
			// Make silly robot to make a step if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { _field.getSillyRobot().makeStep(); }
			// Make silly robot try to catch smart robot if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { _field.getSillyRobot().catchSmartRobot(); }
			// Update season
			_updateSeason();

			// Set that field can be redrawn if smart robot wasn't escaped or caught
			if (_state == GameState.GAME_RUNNING) { fireGameFieldIsChanged(); }
		}

		/** Handle event when smart robot has been destroyed
		 * @param event 		event information
		 */
		public void smartRobotHasBeenDestroyed(SmartRobotEvent event) {
		}
	}
}
