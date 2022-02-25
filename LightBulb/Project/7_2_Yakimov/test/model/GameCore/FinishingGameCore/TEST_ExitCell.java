package model.GameCore.FinishingGameCore;

import model.Cells.StandardCell;
import model.Events.ExitCellEvent;
import model.Events.ExitCellEventListener;
import model.GameCore.*;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TEST_ExitCell {
	// ============ INIT PART ============
	// Events from exit cell
	private ArrayList<ExitCellEvent> exitCellEvents = new ArrayList<>(); // list of gotten events from exit cell

	// Exit cell event observer
	private class ExitCellObserver implements ExitCellEventListener {
		// ============= OPERATIONS =============
		// --------- contract ---------
		/** Handle event when smart robot is teleported to the exit cell
		 * @param event - event information
		 */
		@Override
		public void smartRobotIsTeleported(ExitCellEvent event) { exitCellEvents.add(event); }
	}

	@BeforeEach
	void initEach() {
		exitCellEvents.clear();
	}

	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
	// 2. Game element.
	// 2.1. Placing game element / teleport smart robot.
	// 2.1.1. Successful teleport 	- smart robot is placed on exit cell;
	// 2.1.2. Unsuccessful teleport - smart robot has already a position;
	// 2.1.3. Unsuccessful teleport - smart robot is placed on exit cell twice;
	// 2.1.4. Unsuccessful teleport - two smart robots are placed on exit cell;
	// 2.1.5. Unsuccessful teleport - smart robot is placed on two exit cells;
	// 2.1.6. Unsuccessful teleport - silly robot is placed on exit cell;
	// 2.1.7. Unsuccessful teleport - sewer is placed on exit cell.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		// Create initial state of entity
		ExitCell cell = new ExitCell();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell.addEventListener(exitCellObserver);

		// Check needed state of entity ...
		// ... check that cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that cell hasn't neighbours
		Assertions.assertFalse(cell.hasNeighbour(Direction.UP));
		Assertions.assertFalse(cell.hasNeighbour(Direction.DOWN));
		Assertions.assertFalse(cell.hasNeighbour(Direction.RIGHT));
		Assertions.assertFalse(cell.hasNeighbour(Direction.LEFT));
		// ... check that cell hasn't walls
		Assertions.assertFalse(cell.hasWall(Direction.UP));
		Assertions.assertFalse(cell.hasWall(Direction.DOWN));
		Assertions.assertFalse(cell.hasWall(Direction.RIGHT));
		Assertions.assertFalse(cell.hasWall(Direction.LEFT));
		// ... check that exit cell didn't fire events
		Assertions.assertEquals(0, exitCellEvents.size());
	}

	// ======== GAME ELEMENT ========
	// --- PLACING GAME ELEMENT / TELEPORT SMART ROBOT ---
	// successful teleport - SMART ROBOT is placed ON EXIT CELL
	@Test
	void teleporting_teleportSmartRobot() {
		// Create initial state of entity
		ExitCell cell = new ExitCell();
		SmartRobot smartRobot = new SmartRobot();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell.addEventListener(exitCellObserver);

		// Run testing operation
		boolean successfulPlacing = cell.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing was successful
		Assertions.assertTrue(successfulPlacing);
		// ... check that exit cell fired one event of teleporting smart robot
		ArrayList<ExitCellEvent> expectedEvents = new ArrayList<>();

		ExitCellEvent exitCellEvent = new ExitCellEvent(cell);
		exitCellEvent.setSmartRobot(smartRobot);
		expectedEvents.add(exitCellEvent);

		Assertions.assertEquals(expectedEvents, exitCellEvents);
		// ... check that exit cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that smart robot is caught and hasn't a position
		Assertions.assertTrue(smartRobot.isCaught() && !smartRobot.hasPosition());
	}

	// unsuccessful teleport - smart robot HAS already A POSITION
	@Test
	void teleporting_smartRobotHasPosition() {
		// Create initial state of entity
		ExitCell exitCell = new ExitCell();
		StandardCell cell = new StandardCell();
		SmartRobot smartRobot = new SmartRobot();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		exitCell.addEventListener(exitCellObserver);

		cell.placeGameElement(smartRobot);

		// Run testing operation
		boolean successfulPlacing = exitCell.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that exit cell fired one event of teleporting smart robot
		Assertions.assertEquals(0, exitCellEvents.size());
		// ... check that exit cell is empty
		Assertions.assertFalse(exitCell.containsGameElementType(GameElement.class));
		// ... check that standard cell contains only one smart robot
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertEquals(smartRobot, cell.getGameElements(SmartRobot.class).get(0));
		// ... check that smart robot isn't caught
		Assertions.assertFalse(smartRobot.isCaught());
		// ... check that smart robot has a position and its position is a standard cell
		Assertions.assertTrue(smartRobot.hasPosition() && smartRobot.position() == cell);
	}

	// unsuccessful teleport - SMART ROBOT is placed ON EXIT CELL TWICE
	@Test
	void teleporting_teleportSmartRobotTwice() {
		// Create initial state of entity
		ExitCell cell = new ExitCell();
		SmartRobot smartRobot = new SmartRobot();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell.addEventListener(exitCellObserver);

		cell.placeGameElement(smartRobot);

		// Run testing operation
		boolean successfulPlacing = cell.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that exit cell fired one event of teleporting smart robot
		ArrayList<ExitCellEvent> expectedEvents = new ArrayList<>();

		ExitCellEvent exitCellEvent = new ExitCellEvent(cell);
		exitCellEvent.setSmartRobot(smartRobot);
		expectedEvents.add(exitCellEvent);

		Assertions.assertEquals(expectedEvents, exitCellEvents);
		// ... check that exit cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that smart robot is caught and hasn't a position
		Assertions.assertTrue(smartRobot.isCaught() && !smartRobot.hasPosition());
	}

	// unsuccessful teleport - TWO SMART ROBOTS are placed ON EXIT CELL
	@Test
	void teleporting_teleportTwoSmartRobots() {
		// Create initial state of entity
		ExitCell cell = new ExitCell();
		SmartRobot smartRobot1 = new SmartRobot();
		SmartRobot smartRobot2 = new SmartRobot();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell.addEventListener(exitCellObserver);

		cell.placeGameElement(smartRobot1);

		// Run testing operation
		boolean successfulPlacing = cell.placeGameElement(smartRobot2);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that exit cell fired one event of teleporting smart robot
		ArrayList<ExitCellEvent> expectedEvents = new ArrayList<>();

		ExitCellEvent exitCellEvent = new ExitCellEvent(cell);
		exitCellEvent.setSmartRobot(smartRobot1);
		expectedEvents.add(exitCellEvent);

		Assertions.assertEquals(expectedEvents, exitCellEvents);
		// ... check that exit cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that first smart robot is caught and hasn't a position
		Assertions.assertTrue(smartRobot1.isCaught() && !smartRobot1.hasPosition());
		// ... check that second smart robot isn't caught and hasn't a position
		Assertions.assertTrue(!smartRobot2.isCaught() && !smartRobot2.hasPosition());
	}

	// unsuccessful teleport - SMART ROBOT is placed ON TWO EXIT CELLS
	@Test
	void teleporting_twoExitCellsteleportSmartRobot() {
		// Create initial state of entity
		ExitCell cell1 = new ExitCell();
		ExitCell cell2 = new ExitCell();
		SmartRobot smartRobot = new SmartRobot();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell1.addEventListener(exitCellObserver);
		cell2.addEventListener(exitCellObserver);

		cell1.placeGameElement(smartRobot);

		// Run testing operation
		boolean successfulPlacing = cell2.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that first exit cell fired one event of teleporting smart robot
		ArrayList<ExitCellEvent> expectedEvents = new ArrayList<>();

		ExitCellEvent exitCellEvent = new ExitCellEvent(cell1);
		exitCellEvent.setSmartRobot(smartRobot);
		expectedEvents.add(exitCellEvent);

		Assertions.assertEquals(expectedEvents, exitCellEvents);
		// ... check that first exit cell is empty
		Assertions.assertFalse(cell1.containsGameElementType(GameElement.class));
		// ... check that first exit cell is empty
		Assertions.assertFalse(cell2.containsGameElementType(GameElement.class));
		// ... check that smart robot is caught and hasn't a position
		Assertions.assertTrue(smartRobot.isCaught() && !smartRobot.hasPosition());
	}

	// unsuccessful teleport - SILLY ROBOT is placed ON EXIT CELL
	// Init test maze designer needed for silly robot's field
	private class ForTestMazeDesigner_sillyRobotsField extends MazeDesigner {
		public ForTestMazeDesigner_sillyRobotsField() {
			this._setFieldSize(new RectangleSize(2, 2));
			this._setExitCellPosition(new CellPosition(1, 0));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(0, 0));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 1));
		}
	}

	@Test
	void teleporting_placingSillyRobot() {
		// Create initial state of entity
		Field field 		= new Field(new ForTestMazeDesigner_sillyRobotsField());
		AbstractCell cell 	= null;

		if (field.hasCell(new CellPosition(1, 0))) {
			cell = field.cell(new CellPosition( 1, 0));
		}
		SillyRobot sillyRobot = new SillyRobot(field);

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		if (cell != null && cell instanceof ExitCell) {
			((ExitCell)cell).addEventListener(exitCellObserver);
		}

		// Run testing operation
		boolean successfulPlacing = false;
		if (cell != null) {
			successfulPlacing = cell.placeGameElement(sillyRobot);
		}

		// Check needed state of entity ...
		// ... check that exit cell of field was successfully gotten
		Assertions.assertNotNull(cell);
		Assertions.assertTrue(cell instanceof ExitCell);
		// ... check that placing was successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that exit cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that silly robot hasn't position
		Assertions.assertFalse(sillyRobot.hasPosition());
		// ... check that exit cell didn't fire events
		Assertions.assertEquals(0, exitCellEvents.size());
	}

	// unsuccessful teleport - SEWER is placed ON EXIT CELL
	@Test
	void teleporting_placingSewer() {
	    Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		ExitCell cell = new ExitCell();
		Sewer sewer = new Sewer();

		ExitCellObserver exitCellObserver = new ExitCellObserver();
		cell.addEventListener(exitCellObserver);

		// Run testing operation
		boolean successfulPlacing = cell.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(successfulPlacing);
		// ... check that exit cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that sewer hasn't a position
		Assertions.assertFalse(sewer.hasPosition());
		// ... check that exit cell didn't fire events
		Assertions.assertEquals(0, exitCellEvents.size());
		 */
	}
}
