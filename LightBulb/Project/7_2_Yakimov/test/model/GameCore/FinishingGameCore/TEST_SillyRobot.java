package model.GameCore.FinishingGameCore;

import model.Cells.StandardCell;
import model.Events.AbstractRobotEvent;
import model.Events.SillyRobotEvent;
import model.Events.SillyRobotEventListener;
import model.GameCore.*;
import model.Navigation.BetweenCellsPosition;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TEST_SillyRobot {
	// ============ INIT PART ============
	// Events from silly robot
	private ArrayList<SillyRobotEvent> sillyRobotEvents = new ArrayList<>(); // list of gotten events from silly robot

	// Silly robot event observer
	private class SillyRobotEventObserver implements SillyRobotEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** Handle event when robot has being affected by landscape
		 * @param event - event information
		 */
		public void robotIsAffectedByLandscape(AbstractRobotEvent event) { sillyRobotEvents.add((SillyRobotEvent)event); }

		/** Handle event when robot has being waited when skipping move or moving forced
		 * @param event - event information
		 */
		public void robotHasDoneActionByTimer(AbstractRobotEvent event) { sillyRobotEvents.add((SillyRobotEvent)event); }

		/** Handle event when silly robot got into the sewer
		 * @param event - event information
		 */
		public void gotIntoSewer(SillyRobotEvent event) { sillyRobotEvents.add(event); }

		/** Handle event when silly robot caught the smart robot
		 * @param event - event information
		 */
		public void smartRobotIsCaught(SillyRobotEvent event) { sillyRobotEvents.add(event); }

		/** Handle event when silly robot saw the smart robot in his area of view
		 * @param event - event information
		 */
		public void smartRobotIsSeen(SillyRobotEvent event) { sillyRobotEvents.add(event); }

		/** Handle event when silly robot didn't see the smart robot in his area of view
		 * @param event - event information
		 */
		public void smartRobotIsHiding(SillyRobotEvent event) { sillyRobotEvents.add(event); }
	}

	@BeforeEach
	void initEach() { sillyRobotEvents.clear(); }

	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
	// 2. Moving;
	// 2.1. Making a step in silly way.
	// 2.1.1. Unsuccessful moving 	- making a step without having position;
	// 2.1.2. Successful moving 	- simple making a step;
	// 2.1.3. Unsuccessful moving 	- making a step to wall;
	// 2.1.4. Successful moving 	- making a step to exit cell;
	// 2.1.5. Successful moving 	- making a step to sewer;
	// 2.1.6. Unsuccessful moving 	- making a step to silly robot;
	// 2.1.7. Successful moving 	- making a step after getting to sewer.
	// 2.2. Making a step in smart way.
	// 2.2.1. Unsuccessful moving 	- making a step without having position;
	// 2.2.2. Successful moving 	- simple making a step;
	// 2.2.3. Successful moving 	- making a step to exit cell;
	// 2.2.4. Successful moving 	- making a step to sewer;
	// 2.2.5. Successful moving 	- making a step to smart robot;
	// 2.2.6. Successful moving 	- making a step after getting to sewer;
	// 2.2.7. Unsuccessful moving 	- silly robot can't go through obstacles to smart robot;
	// 2.2.8. Unsuccessful moving 	- smart robot has moved on the exit cell.
	//
	// 3. Catch smart robot.
	// 3.1. Catch smart robot.
	// 3.1.1. Successful catching 	- smart robot is on the same cell;
	// 3.1.2. Unsuccessful catching - smart robot is on the different cell.

	// ========== CREATION ==========
	// Init test maze designer for testing creation
	private class ForTestMazeDesigner_creation extends MazeDesigner {
		public ForTestMazeDesigner_creation() {
			this._setFieldSize(new RectangleSize(3, 3));
			this._setExitCellPosition(new CellPosition(1, 1));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(2, 1));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(0, 1));
		}
	}

	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		// Create initial state of entity
		Field field = new Field(new ForTestMazeDesigner_creation());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// Check needed state of entity ...
		// ... check that silly robot hasn't a position
		Assertions.assertFalse(sillyRobot.hasPosition());
		// ... check that listeners of silly robot haven't got any fired events
		Assertions.assertTrue(sillyRobotEvents.isEmpty());
	}

	// =========== MOVING ===========
	// Init test maze designer for testing making a step in silly way
	private class ForTestMazeDesigner_makingStepInSillyWay extends MazeDesigner {
		public ForTestMazeDesigner_makingStepInSillyWay() {
			this._setFieldSize(new RectangleSize(7, 5));
			this._setExitCellPosition(new CellPosition(3, 2));

			this._addWallInfo(new BetweenCellsPosition(new CellPosition(2, 3), Direction.RIGHT));

			// this._addGameElementInfo(Sewer.class, new CellPosition(3, 1));
			this._addGameElementInfo(SmartRobot.class, new CellPosition(6, 2));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 2));
		}
	}

	// --- MAKING A STEP IN SILLY WAY ---
	// Unsuccessful moving - making a step WITHOUT HAVING POSITION
	@Test
	void makeStep_inSillyWay_makingStepWithoutHavingPosition() {
		// Create initial state of entity
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// Run testing operation
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()->((SillyRobot)sillyRobot).makeStep());

		// Check needed state of entity ...
		// ... check that silly robot throw exception
		Assertions.assertNotEquals("", exception.getMessage());
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - SIMPLE making a step
	@Test
	void makeStep_inSillyWay_simpleMakingStep() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(1, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the next cell of start silly robot's position
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(2, 1)));
		Assertions.assertEquals(field.cell(new CellPosition(2, 1)), sillyRobot.position());
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Unsuccessful moving - making a step TO the WALL
	@Test
	void makeStep_inSillyWay_makingStepToWall() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 3);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the same
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(2, 3)));
		Assertions.assertEquals(field.cell(new CellPosition(2, 3)), sillyRobot.position());
		// ... check that silly robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the EXIT CELL
	@Test
	void makeStep_inSillyWay_makingStepToExitCell() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 2);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the standard cell
		Assertions.assertTrue(sillyRobot.hasPosition() && sillyRobot.position() instanceof StandardCell);
		// ... check that silly robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the CELL WITH SEWER
	@Test
	void makeStep_inSillyWay_makingStepToSewer() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position contains the sewer
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(sillyRobot.position().containsGameElementType(Sewer.class));
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the CELL WITH SILLY ROBOT
	@Test
	void makeStep_inSillyWay_makingStepToSillyRobot() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(0, 2);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the same
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(0, 2)));
		Assertions.assertEquals(field.cell(new CellPosition(0, 2)), sillyRobot.position());
		// ... check that silly robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step AFTER BEING IN SEWER
	@Test
	void makeStep_inSillyWay_makingStepAfterGettingIntoSewer() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSillyWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// ... move silly robot to sewer and skip there 3 moves
		if (sillyRobot.hasPosition()) {
			sillyRobot.makeStep();

			for (int waitInSewerCount=0; waitInSewerCount<3; waitInSewerCount++) { sillyRobot.makeStep(); }
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the next cell of start silly robot's position
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(4, 1)));
		Assertions.assertEquals(field.cell(new CellPosition(4, 1)), sillyRobot.position());
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// --- MAKING A STEP IN SMART WAY ---
	// Init test maze designer for testing making a step in silly way
	private class ForTestMazeDesigner_makingStepInSmartWay extends MazeDesigner {
		public ForTestMazeDesigner_makingStepInSmartWay() {
			this._setFieldSize(new RectangleSize(5, 5));
			this._setExitCellPosition(new CellPosition(2, 1));

			this._addWallInfo(new BetweenCellsPosition(new CellPosition(4, 0), Direction.LEFT));
			this._addWallInfo(new BetweenCellsPosition(new CellPosition(4, 0), Direction.DOWN));

			// this._addGameElementInfo(Sewer.class, new CellPosition(1, 2));
			this._addGameElementInfo(SmartRobot.class, new CellPosition(2, 2));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(3, 2));
		}
	}

	// Unsuccessful moving - making a step WITHOUT HAVING POSITION
	@Test
	void makeStep_inSmartWay_makingStepWithoutHavingPosition() {
		// Create initial state of entity
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// Run testing operation
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()-> ((SillyRobot)sillyRobot).makeStep());

		// Check needed state of entity ...
		// ... check that silly robot throw exception
		Assertions.assertNotEquals("", exception.getMessage());
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - SIMPLE making a step
	@Test
	void makeStep_inSmartWay_simpleMakingStep() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 4);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(2, 3)));
		Assertions.assertEquals(field.cell(new CellPosition(2, 3)), sillyRobot.position());
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the EXIT CELL
	@Test
	void makeStep_inSmartWay_makingStepToExitCell() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 0);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the standard cell
		Assertions.assertTrue(sillyRobot.hasPosition() && sillyRobot.position() instanceof StandardCell);
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the CELL WITH SEWER
	@Test
	void makeStep_inSmartWay_makingStepToSewer() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(0, 2);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position contains the sewer
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(sillyRobot.position().containsGameElementType(Sewer.class));
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step TO the CELL WITH SMART ROBOT
	@Test
	void makeStep_inSmartWay_makingStepToSmartRobot() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 3);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position contains smart robot
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(sillyRobot.position().containsGameElementType(SmartRobot.class));
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Successful moving - making a step AFTER BEING IN SEWER
	@Test
	void makeStep_inSmartWay_makingStepAfterBeingInSewer() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(0, 2);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// ... move silly robot to sewer and skip there 3 moves
		if (sillyRobot.hasPosition()) {
			sillyRobot.makeStep();

			for (int waitInSewerCount=0; waitInSewerCount<3; waitInSewerCount++) { sillyRobot.makeStep(); }
		}

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position is the next cell of start silly robot's position
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertTrue(field.hasCell(new CellPosition(2, 2)));
		Assertions.assertEquals(field.cell(new CellPosition(2, 2)), sillyRobot.position());
		// ... check that silly robot was successfully made a step
		Assertions.assertTrue(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Unsuccessful moving - SILLY ROBOT IS BLOCKED
	@Test
	void makeStep_inSmartWay_sillyRobotIsBlockedByObstacles() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(4, 0);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// Run testing operation
		boolean successfulMakingStep = true;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that silly robot has a position and its position equals its initial position
		Assertions.assertTrue(sillyRobot.hasPosition());
		Assertions.assertEquals(field.cell(new CellPosition(4, 0)), sillyRobot.position());
		// ... check that silly robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Unsuccessful moving - making a step to SMART ROBOT which HAS MOVED TO EXIT CELL
	@Test
	void makeStep_inSmartWay_smartRobotGotHasMovedToExitCell() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_makingStepInSmartWay());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(2, 4);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// ... move smart robot to exit cell
        field.getSmartRobot().makeStep(Direction.UP);

		// Run testing operation
		boolean successfulMakingStep = false;
		if (sillyRobot.hasPosition()) {
			successfulMakingStep = sillyRobot.makeStep();
		}

		// Check needed state of entity ...
		// ... check that smart robot was successfully moved to exit cell
        SmartRobot smartRobot = field.getSmartRobot();
		Assertions.assertTrue(smartRobot.isCaught() && !smartRobot.hasPosition());
		// ... check that silly robot has a position
		Assertions.assertTrue(sillyRobot.hasPosition());
		// ... check that silly robot wasn't successfully made a step
		Assertions.assertFalse(successfulMakingStep);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// ========= SMART ROBOT =========
	// Init test maze designer for testing smart robot's catching
	private class ForTestMazeDesigner_catchSmartRobot extends MazeDesigner {
		public ForTestMazeDesigner_catchSmartRobot() {
			this._setFieldSize(new RectangleSize(2, 2));
			this._setExitCellPosition(new CellPosition(0, 0));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(1, 0));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 1));
		}
	}

	// --- CATCHING SMART ROBOT ---
	// Successful catching - smart robot is on the same cell
	@Test
	void catchSmartRobot_smartRobotIsOnTheSameCell() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_catchSmartRobot());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(1, 0);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// ... get smart robot from field
		CellPosition smartRobotCellPosition = new CellPosition(1, 0);
		AbstractCell smartRobotCell = field.cell(smartRobotCellPosition);

		SmartRobot smartRobot = null;
		if (smartRobotCell.containsGameElementType(SmartRobot.class)) {
			smartRobot = (SmartRobot)smartRobotCell.getGameElements(SmartRobot.class).get(0);
		}

		// Run testing operation
		boolean successfulCatching = false;
		if (sillyRobot.hasPosition()) {
			successfulCatching = sillyRobot.catchSmartRobot();
		}

		// Check needed state of entity ...
		// ... check that smart robot was successfully caught by silly robot
		Assertions.assertNotNull(smartRobot);
		Assertions.assertTrue(!smartRobot.hasPosition() && smartRobot.isCaught());
		// ... check that silly robot has a position
		Assertions.assertTrue(sillyRobot.hasPosition());
		// ... check that silly robot successfully caught smart robot
		Assertions.assertTrue(successfulCatching);
		// ... check that listeners got the same events as expected
		ArrayList<SillyRobotEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(new SillyRobotEvent(sillyRobot));

		Assertions.assertEquals(expectedEvents, sillyRobotEvents);
	}

	// Unsuccessful catching - Smart robot is on the different cell
	@Test
	void catchSmartRobot_smartRobotIsOnTheDifferentCell() {
		// Create initial state of entity ...
		// ... create silly robot
		Field field = new Field(new ForTestMazeDesigner_catchSmartRobot());

		SillyRobotEventObserver sillyRobotObserver = new SillyRobotEventObserver();

		SillyRobot sillyRobot = new SillyRobot(field);
		sillyRobot.addEventListener(sillyRobotObserver);

		// ... create field and set silly robot's position as cell of created field
		CellPosition cellPosition = new CellPosition(0, 1);
		if (field.hasCell(cellPosition)) {
			field.cell(cellPosition).placeGameElement(sillyRobot);
		}

		// ... get smart robot from field
		CellPosition smartRobotCellPosition = new CellPosition(1, 0);
		AbstractCell smartRobotCell = field.cell(smartRobotCellPosition);

		SmartRobot smartRobot = null;
		if (smartRobotCell.containsGameElementType(SmartRobot.class)) {
			smartRobot = (SmartRobot)smartRobotCell.getGameElements(SmartRobot.class).get(0);
		}

		// Run testing operation
		boolean successfulCatching = false;
		if (sillyRobot.hasPosition()) {
			successfulCatching = sillyRobot.catchSmartRobot();
		}

		// Check needed state of entity ...
		// ... check that smart robot was successfully caught by silly robot
		Assertions.assertNotNull(smartRobot);
		Assertions.assertTrue(smartRobot.hasPosition() && !smartRobot.isCaught());
		// ... check that silly robot has a position
		Assertions.assertTrue(sillyRobot.hasPosition());
		// ... check that silly robot successfully caught smart robot
		Assertions.assertFalse(successfulCatching);
		// ... check that listeners of silly robot haven't got any fired events
		Assertions.assertTrue(sillyRobotEvents.isEmpty());
	}
}