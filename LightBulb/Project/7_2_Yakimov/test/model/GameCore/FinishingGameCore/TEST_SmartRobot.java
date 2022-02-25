package model.GameCore.FinishingGameCore;

import model.Events.AbstractRobotEvent;
import model.Events.SmartRobotEvent;
import model.Events.SmartRobotEventListener;
import model.GameCore.*;
import model.Navigation.BetweenCellsPosition;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TEST_SmartRobot {
	// ============ INIT PART ============
	// Events from smart robot
	private ArrayList<SmartRobotEvent> smartRobotEvents = new ArrayList<>(); // list of gotten events from smart robot

	// Smart robot event observer
	private class SmartRobotEventObserver implements SmartRobotEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** Handle event when robot has being affected by landscape
		 * @param event - event information
		 */
		public void robotIsAffectedByLandscape(AbstractRobotEvent event) { smartRobotEvents.add((SmartRobotEvent)event); }

		/** Handle event when robot has being waited when skipping move or moving forced
		 * @param event - event information
		 */
		public void robotHasDoneActionByTimer(AbstractRobotEvent event) { smartRobotEvents.add((SmartRobotEvent)event); }

		/** Handle event when smart robot has made a step on the field
		 * @param event - event information
		 */
		public void smartRobotHasMadeAStep(SmartRobotEvent event) { smartRobotEvents.add(event); }

		/** Handle event when smart robot has skipped current move
		 * @param event - event information
		 */
		public void smartRobotHasSkippedMove(SmartRobotEvent event) { smartRobotEvents.add(event); }

		/** Handle event when smart robot has been destroyed
		 * @param event 		event information
		 */
		public void smartRobotHasBeenDestroyed(SmartRobotEvent event) { smartRobotEvents.add(event); }
	}

	@BeforeEach
	void initEach() { smartRobotEvents.clear(); }

	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
	// 2. Moving.
	// 2.1. Making a step.
	// 2.1.1. Unsuccessful moving 	- making a step without having position;
	// 2.1.2. Successful moving 	- simple making a step;
	// 2.1.3. Unsuccessful moving 	- making a step to wall;
	// 2.1.4. Unsuccessful moving 	- making a step to end of field;
	// 2.1.5. Successful moving 	- making a step to exit cell;
	// 2.1.6. Unsuccessful moving 	- making a step to sewer;
	// 2.1.7. Successful moving 	- making a step to cell with silly robot;
	// 2.1.8. Unsuccessful moving 	- making a step to smart robot.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		// Create initial state of entity
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// Check needed state of entity ...
		// ... check that smart robot hasn't a position
		Assertions.assertFalse(smartRobot.hasPosition());
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}

	// ========== MOVING ==========
	// Init test maze designer for testing getting cell at given position or cells from given area
	private class ForTestMazeDesigner_makeStep extends MazeDesigner {
		public ForTestMazeDesigner_makeStep() {
			this._setFieldSize(new RectangleSize(5, 5));
			this._setExitCellPosition(new CellPosition(3, 2));

			this._addWallInfo(new BetweenCellsPosition(new CellPosition(1, 1), Direction.RIGHT));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(1, 3), Direction.RIGHT));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(0, 2), Direction.UP));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(0, 2), Direction.RIGHT));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(0, 2), Direction.DOWN));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(1, 4), Direction.RIGHT));

			// this._addGameElementInfo(Sewer.class, new CellPosition(2, 2));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(4, 1));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(4, 3));
		}
	}

	// --- MAKING A STEP ---
	// Unsuccessful moving - making step without having position
	@Test
	void makeStep_makingStepWithoutHavingPosition() {
		// Create initial state of entity
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// Run testing operation
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> ((SmartRobot)smartRobot).makeStep(Direction.UP));

		// Check needed state of entity ...
		// ... check that smart robot throw exception
		Assertions.assertNotEquals("", exception.getMessage());
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}

	// Successful moving - SIMPLE making step
	@Test
	void makeStep_simpleMakingStep() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(1, 0);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SmartRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SmartRobotEvent(smartRobot));

		Assertions.assertEquals(expectedEvents, smartRobotEvents);
	}

	// Unsuccessful moving - making step to the wall
	@Test
	void makeStep_makingStepToWall() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(1, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = true;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}

	// Unsuccessful moving - making step to the end of the field
	@Test
	void makeStep_makingStepToFieldEnd() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(4, 0);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = true;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}

	// Successful moving - making step to the exit cell
	@Test
	void makeStep_makingStepToExitCell() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(3, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.DOWN);
		}

		// Check needed state of entity ...
		// ... check that cell exists at initial cell position
		Assertions.assertTrue(field.hasCell(cellPosition));
		// ... check that smart robot has a position
		Assertions.assertFalse(smartRobot.hasPosition());
		// ... check that smart robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SmartRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SmartRobotEvent(smartRobot));

		Assertions.assertEquals(expectedEvents, smartRobotEvents);
	}

	// Unsuccessful moving - making step to the sewer
	@Test
	void makeStep_makingStepToSewer() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(1, 2);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = true;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}

	// Successful moving - making step to the cell with silly robot
	@Test
	void makeStep_makingStepToSillyRobot() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(3, 3);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SmartRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SmartRobotEvent(smartRobot));

		Assertions.assertEquals(expectedEvents, smartRobotEvents);
	}

	// Unsuccessful moving - making step to the cell with smart robot
	@Test
	void makeStep_makingStepToSmartRobot() {
		// Create initial state of entity ...
		// ... create smart robot
		SmartRobotEventObserver smartRobotObserver = new SmartRobotEventObserver();

		SmartRobot smartRobot = new SmartRobot();
		smartRobot.addEventListener(smartRobotObserver);

		// ... create field and set smart robot's position as cell of created field
		Field field = new Field(new ForTestMazeDesigner_makeStep());

		CellPosition cellPosition = new CellPosition(3, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(smartRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = true;
		if (smartRobot.hasPosition()) {
			successfulMakingStep = smartRobot.makeStep(Direction.RIGHT);
		}

		// Check needed state of entity ...
		// ... check that smart robot has a position
		Assertions.assertTrue(smartRobot.hasPosition());
		// ... check that smart robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners of smart robot haven't got any fired events
		Assertions.assertTrue(smartRobotEvents.isEmpty());
	}
}
