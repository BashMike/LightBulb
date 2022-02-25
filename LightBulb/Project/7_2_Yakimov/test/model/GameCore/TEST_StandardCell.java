package model.GameCore;

import model.Cells.StandardCell;
import model.GameCore.FinishingGameCore.ExitCell;
import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TEST_StandardCell {
	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
	// 2. Neighbours;
	// 2.1. Setting a neighbour.
	// 2.1.1. Simple setting;
	// 2.1.2. Double-time setting;
	// 2.1.3. Setting right neighbour as left neighbour;
	// 2.1.4. Setting yourself as neighbour.
	//
	// 3. Walls;
	// 3.1. Setting a wall.
	// 3.1.1. Simple setting;
	// 3.1.2. Double-time setting;
	// 3.1.3. Setting a wall with neighbour;
	// 3.1.4. Setting a wall with neighbour when neighbour also set a wall.
	//
	// 4. Game element.
	// 4.1. Placing game element;
	// 4.1.1. Place in empty cell;
	// 4.1.2. Place the same game element;
	// 4.1.3. Place in filled cell;
	// 4.1.4. Game element defines that he can't be placed;
	// 4.1.5. Game element has already a position;
	// 4.1.6. Cell already has game element with type of given game element;
	// 4.1.7. Place caught smart robot;
	// 4.1.8. Place silly robot on one field to cell of another field;
	// 4.1.9. Inner game elements can't be in the same cell with given game element.
	// 4.2. Removing game element;
	// 4.2.1. Game element has position;
	// 4.2.2. Game element hasn't position.
	// 4.3. Getting game element.
	// 4.3.1. Get all game elements;
	// 4.3.2. Get all robots;
	// 4.3.3. Get all sewers.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		// Create initial state of entity
		StandardCell cell = new StandardCell();

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
	}

	// ========= NEIGHBOURS =========
	// --- SETTING A NEIGHBOUR ---
	// SIMPLE setting
	@Test
	void setNeighbour_simpleSetting() {
		// Create initial state of entity
		AbstractCell cell1 = new StandardCell();
		AbstractCell cell2 = new StandardCell();

		Direction direction = Direction.RIGHT;

		// Running testing operation
		cell1.setNeighbour(direction, cell2);

		// Check needed state of entity ...
		// ... check that first cell has neighbour and its neighbour is second cell
		Assertions.assertTrue(cell1.hasNeighbour(direction) && cell1.neighbour(direction) == cell2);
		// ... check that second cell has neighbour and its neighbour is first cell
		Assertions.assertTrue(cell2.hasNeighbour(direction.opposite()) && cell2.neighbour(direction.opposite()) == cell1);
	}

	// DOUBLE-TIME setting
	@Test
	void setNeighbour_secondSetting() {
		// Create initial state of entity
		AbstractCell cell1 = new StandardCell();
		AbstractCell cell2 = new StandardCell();

		Direction direction = Direction.RIGHT;
		cell1.setNeighbour(direction, cell2);

		// Running testing operation
		cell1.setNeighbour(direction, cell2);

		// Check needed state of entity ...
		// ... check that first cell has neighbour and its neighbour is second cell
		Assertions.assertTrue(cell1.hasNeighbour(direction) && cell1.neighbour(direction) == cell2);
		// ... check that second cell has neighbour and its neighbour is first cell
		Assertions.assertTrue(cell2.hasNeighbour(direction.opposite()) && cell2.neighbour(direction.opposite()) == cell1);
	}

	// Setting RIGHT NEIGHBOUR AS LEFT neighbour
	@Test
	void setNeighbour_settingNeighbourAsAnotherNeighbour() {
		// Create initial state of entity
		AbstractCell cell1 = new StandardCell();
		AbstractCell cell2 = new StandardCell();

		Direction direction = Direction.RIGHT;
		cell1.setNeighbour(direction, cell2);

		// Running testing operation
		cell1.setNeighbour(direction.opposite(), cell2);

		// Check needed state of entity ...
		// ... check that first cell has neighbour and its neighbour is second cell
		Assertions.assertTrue(cell1.hasNeighbour(direction) && cell1.neighbour(direction) == cell2);
		// ... check that first cell hasn't neighbour in opposite direction of initial setting
		Assertions.assertFalse(cell1.hasNeighbour(direction.opposite()));
		// ... check that second cell has neighbour and its neighbour is first cell
		Assertions.assertTrue(cell2.hasNeighbour(direction.opposite()) && cell2.neighbour(direction.opposite()) == cell1);
	}

	// Setting YOURSELF AS NEIGHBOUR
	@Test
	void setNeighbour_yourselfAsNeighbour() {
		// Create initial state of entity
		AbstractCell cell = new StandardCell();

		Direction direction = Direction.RIGHT;

		// Running testing operation
		cell.setNeighbour(direction.opposite(), cell);

		// Check needed state of entity ...
		// ... check that cell has no neighbour in given direction
		Assertions.assertFalse(cell.hasNeighbour(direction));
	}

	// =========== WALLS ============
	// --- SETTING A WALL ---
	// SIMPLE setting
	@Test
	void setWall_simpleSetting() {
		// Create initial state of entity
		AbstractCell cell = new StandardCell();

		Direction direction = Direction.RIGHT;

		// Running testing operation
		cell.setWall(direction);

		// Check needed state of entity ...
		// ... check that cell has wall
		Assertions.assertTrue(cell.hasWall(direction));
	}

	// DOUBLE-TIME setting
	@Test
	void setWall_secondSetting() {
		// Create initial state of entity
		AbstractCell cell = new StandardCell();

		Direction direction = Direction.RIGHT;
		cell.setWall(direction);

		// Running testing operation
		cell.setWall(direction);

		// Check needed state of entity ...
		// ... check that cell has wall
		Assertions.assertTrue(cell.hasWall(direction));
	}

	// Setting wall with NEIGHBOUR
	@Test
	void setWall_withNeighbour() {
		// Create initial state of entity
		AbstractCell cell1 = new StandardCell();
		AbstractCell cell2 = new StandardCell();

		Direction direction = Direction.RIGHT;
		cell1.setNeighbour(direction, cell2);

		// Running testing operation
		cell1.setWall(direction);

		// Check needed state of entity ...
		// ... check that first cell has wall
		Assertions.assertTrue(cell1.hasWall(direction));
		// ... check that second cell has wall
		Assertions.assertTrue(cell2.hasWall(direction.opposite()));
	}

	// Setting a wall with neighbour when NEIGHBOUR ALSO SET a wall.
	@Test
	void setWall_neighbourAlsoSetSameWall() {
		// Create initial state of entity
		AbstractCell cell1 = new StandardCell();
		AbstractCell cell2 = new StandardCell();

		Direction direction = Direction.RIGHT;
		cell1.setNeighbour(direction, cell2);
		cell1.setWall(direction);

		// Running testing operation
		cell2.setWall(direction.opposite());

		// Check needed state of entity ...
		// ... check that first cell has wall
		Assertions.assertTrue(cell1.hasWall(direction));
		// ... check that second cell has wall
		Assertions.assertTrue(cell2.hasWall(direction.opposite()));
	}

	// ======== GAME ELEMENT ========
	// Init test maze designer needed for silly robot's field
	private class ForTestMazeDesigner_sillyRobotsField extends MazeDesigner {
		public ForTestMazeDesigner_sillyRobotsField() {
			this._setFieldSize(new RectangleSize(2, 2));
			this._setExitCellPosition(new CellPosition(1, 0));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(0, 0));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 1));
		}
	}

	// --- PLACE GAME ELEMENT ---
	// successful placing - placing on EMPTY CELL
	@Test
	void placeGameElement_emptyCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 	= new StandardCell();
		Sewer sewer 		= new Sewer();

		// Run testing function
		boolean placingIsSuccessful = cell.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that placing was successful
		Assertions.assertTrue(placingIsSuccessful);
		// ... check that cell has only sewer in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertTrue(cell.containsGameElementType(Sewer.class));
		// ... check that sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer.hasPosition() && sewer.position() == cell);
		 */
	}

	// successful placing - placing the SAME GAME ELEMENT
	@Test
	void placeGameElement_placingSameGameElement() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer 			= new Sewer();

		cell.placeGameElement(sewer);

		// Run testing operation
		boolean placingIsSuccessful = cell.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that placing was successful
		Assertions.assertTrue(placingIsSuccessful);
		// ... check that cell has only sewer in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertEquals(sewer, cell.getGameElements(GameElement.class).get(0));
		// ... check that sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer.hasPosition() && sewer.position() == cell);
		 */
	}

	// successful placing - placing on FILLED CELL
	@Test
	void placeGameElement_filledCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		Field field 		= new Field(new ForTestMazeDesigner_sillyRobotsField());
		AbstractCell cell 	= null;

		if (field.hasCell(new CellPosition(0, 1))) {
			cell = field.cell(new CellPosition( 0, 1));
		}

		Sewer sewer = new Sewer();
		if (cell != null) {
			cell.placeGameElement(sewer);
		}

		SillyRobot sillyRobot = new SillyRobot(field);

		// Run testing operation
		boolean placingIsSuccessful = false;
		if (cell != null) {
			placingIsSuccessful = cell.placeGameElement(sillyRobot);
		}

		// Check needed state of entity ...
		// ... check that cell of field was successfully gotten
		Assertions.assertNotNull(cell);
		// ... check that placing was successful
		Assertions.assertTrue(placingIsSuccessful);
		// ... check that cell has sewer and silly robot in it
		Assertions.assertEquals(2, cell.getGameElements(GameElement.class).size());
		Assertions.assertTrue(cell.containsGameElementType(Sewer.class));
		Assertions.assertTrue(cell.containsGameElementType(SillyRobot.class));
		// ... check that sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer.hasPosition() && sewer.position() == cell);
		// ... check that silly robot has position and its position is an initial cell
		Assertions.assertTrue(sillyRobot.hasPosition() && sillyRobot.position() == cell);
		 */
	}

	// unsuccessful placing - game element CAN'T BE PLACED
	@Test
	void placeGameElement_cantBePlacedInCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer 			= new Sewer();
		SmartRobot smartRobot 	= new SmartRobot();

		cell.placeGameElement(sewer);

		// Run testing operation
		boolean placingIsSuccessful = cell.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing was successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that cell has only sewer in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertTrue(cell.containsGameElementType(Sewer.class));
		// ... check that sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer.hasPosition() && sewer.position() == cell);
		// ... check that silly robot hasn't a position
		Assertions.assertFalse(smartRobot.hasPosition());
		 */
	}

	// unsuccessful placing - placing game element which ALREADY HAS A POSITION
	@Test
	void placeGameElement_placingGameElementWithPosition() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell1 		= new StandardCell();
		StandardCell cell2 		= new StandardCell();
		Sewer sewer 			= new Sewer();

		cell1.placeGameElement(sewer);

		// Run testing operation
		boolean placingIsSuccessful = cell2.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that first cell has only sewer in it
		Assertions.assertEquals(1, cell1.getGameElements(GameElement.class).size());
		Assertions.assertTrue(cell1.containsGameElementType(Sewer.class));
		// ... check that second cell is empty
		Assertions.assertFalse(cell2.containsGameElementType(GameElement.class));
		// ... check that sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer.hasPosition() && sewer.position() == cell1);
		 */
	}

	// unsuccessful placing - cell ALREADY HAS GAME ELEMENT WITH TYPE of given game element
	@Test
	void placeGameElement_placingSameGameElementType() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer1 			= new Sewer();
		Sewer sewer2 			= new Sewer();

		cell.placeGameElement(sewer1);

		// Run testing operation
		boolean placingIsSuccessful = cell.placeGameElement(sewer2);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that cell has only first sewer in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertEquals(sewer1, cell.getGameElements(GameElement.class).get(0));
		// ... check that first sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer1.hasPosition() && sewer1.position() == cell);
		// ... check that second sewer hasn't a position
		Assertions.assertFalse(sewer2.hasPosition());
		 */
	}

	// unsuccessful placing - place CAUGHT SMART ROBOT
	@Test
	void placeGameElement_placeCaughtSmartRobot() {
		// Create initial state of entity
		ExitCell exitCell 		= new ExitCell();
		SmartRobot smartRobot	= new SmartRobot();
		exitCell.placeGameElement(smartRobot);

		StandardCell cell 		= new StandardCell();

		// Run testing operation
		boolean placingIsSuccessful = cell.placeGameElement(smartRobot);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that smart robot is caught and hasn't a position
		Assertions.assertTrue(smartRobot.isCaught() && !smartRobot.hasPosition());
	}

	// unsuccessful placing - place SILLY ROBOT OF ONE FIELD TO cell of ANOTHER FIELD
	@Test
	void placeGameElement_placeSillyRobotOfOneFieldToCellOfAnotherField() {
		// Create initial state of entity
		SillyRobot sillyRobot	= new SillyRobot(new Field(new ForTestMazeDesigner_sillyRobotsField()));

		Field field = new Field(new ForTestMazeDesigner_sillyRobotsField());
		AbstractCell cell = null;

		if (field.hasCell(new CellPosition(0, 1))) {
			cell = field.cell(new CellPosition(0, 1));
		}

		// Run testing operation
		boolean placingIsSuccessful = true;
		if (cell != null) {
			placingIsSuccessful = cell.placeGameElement(sillyRobot);
		}

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that silly robot hasn't a position
		Assertions.assertFalse(sillyRobot.hasPosition());
	}

	// unsuccessful placing - INNER game elements CAN'T BE IN THE SAME CELL with given game element
	@Test
	void placeGameElement_innerGameElementsCantBeInSameCellWithGameElement() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer 			= new Sewer();
		SmartRobot smartRobot 	= new SmartRobot();

		cell.placeGameElement(smartRobot);

		// Run testing operation
		boolean placingIsSuccessful = cell.placeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that placing wasn't successful
		Assertions.assertFalse(placingIsSuccessful);
		// ... check that cell has only smart robot in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertTrue(cell.containsGameElementType(SmartRobot.class));
		// ... check that smart robot has position and its position is an initial cell
		Assertions.assertTrue(smartRobot.hasPosition() && smartRobot.position() == cell);
		// ... check that sewer hasn't a position
		Assertions.assertFalse(sewer.hasPosition());
		 */
	}

	// --- REMOVE GAME ELEMENT ---
	// successful removing - STANDARD removing
	@Test
	void removeGameElement_successfulRemoving() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer 			= new Sewer();

		cell.placeGameElement(sewer);

		// Run testing operation
		boolean successfulRemoving = cell.removeGameElement(sewer);

		// Check needed state of entity ...
		// ... check that removing was successful
		Assertions.assertTrue(successfulRemoving);
		// ... check that cell is empty
		Assertions.assertFalse(cell.containsGameElementType(GameElement.class));
		// ... check that sewer hasn't position
		Assertions.assertFalse(sewer.hasPosition());
		 */
	}

	// unsuccessful removing - game element WITHOUT POSITION
	@Test
	void removeGameElement_removedGameElementIsntOnCell() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell 		= new StandardCell();
		Sewer sewer1 			= new Sewer();
		Sewer sewer2 			= new Sewer();

		cell.placeGameElement(sewer1);

		// Run testing operation
		boolean successfulRemoving = cell.removeGameElement(sewer2);

		// Check needed state of entity ...
		// ... check that removing was successful
		Assertions.assertFalse(successfulRemoving);
		// ... check that cell has only first sewer in it
		Assertions.assertEquals(1, cell.getGameElements(GameElement.class).size());
		Assertions.assertEquals(sewer1, cell.getGameElements(GameElement.class).get(0));
		// ... check that first sewer has position and its position is an initial cell
		Assertions.assertTrue(sewer1.hasPosition() && sewer1.position() == cell);
		// ... check that second sewer hasn't a position
		Assertions.assertFalse(sewer2.hasPosition());
		 */
	}

	// --- GET GAME ELEMENTS ---
	// successful getting - get ALL game elements of cell
	@Test
	void getGameElements_getAllGameElements() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		Field field 		= new Field(new ForTestMazeDesigner_sillyRobotsField());
		AbstractCell cell 	= null;

		if (field.hasCell(new CellPosition(0, 1))) {
			cell = field.cell(new CellPosition( 0, 1));
		}

		ArrayList<GameElement> gameElements = new ArrayList<>();
		gameElements.add(new Sewer());
		gameElements.add(new SillyRobot(field));

		for (var gameElement : gameElements) {
			if (cell != null) {
				cell.placeGameElement(gameElement);
			}
		}

		// Run testing operation
		ArrayList<GameElement> gottenGameElements = new ArrayList<>();
		if (cell != null) {
			gottenGameElements = cell.getGameElements(GameElement.class);
		}

		// Check needed state of entity ...
		// ... check that cell of field was successfully gotten
		Assertions.assertNotNull(cell);
		// ... check that gotten game elements are the same as created game elements
		Assertions.assertEquals(gameElements, gottenGameElements);
		 */
	}

	// successful getting - get ROBOTS of cell
	@Test
	void getGameElements_getAllRobots() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		Field field 		= new Field(new ForTestMazeDesigner_sillyRobotsField());
		AbstractCell cell 	= null;

		if (field.hasCell(new CellPosition(0, 1))) {
			cell = field.cell(new CellPosition( 0, 1));
		}

		ArrayList<GameElement> gameElements = new ArrayList<>();
		gameElements.add(new Sewer());
		gameElements.add(new SillyRobot(field));

		ArrayList<GameElement> robots = new ArrayList<>();
		for (var gameElement : gameElements) {
			if (cell != null) {
				cell.placeGameElement(gameElement);

				if (gameElement instanceof AbstractRobot) {
					robots.add(gameElement);
				}
			}
		}

		// Run testing operation
		ArrayList<GameElement> gottenRobots = new ArrayList<>();
		if (cell != null) {
			gottenRobots = cell.getGameElements(AbstractRobot.class);
		}

		// Check needed state of entity ...
		// ... check that cell of field was successfully gotten
		Assertions.assertNotNull(cell);
		// ... check that gotten game elements are the same as created game elements
		Assertions.assertEquals(robots, gottenRobots);
		 */
	}

	// successful getting - get SEWER
	@Test
	void getGameElements_getSewer() {
		Assertions.assertFalse(true);
		/*
		// Create initial state of entity
		StandardCell cell = new StandardCell();

		ArrayList<GameElement> gameElements = new ArrayList<>();
		gameElements.add(new Sewer());
		gameElements.add(new SillyRobot(new Field(new ForTestMazeDesigner_sillyRobotsField())));

		ArrayList<GameElement> sewers = new ArrayList<>();
		for (var gameElement : gameElements) {
			if (gameElement instanceof Sewer) {
				sewers.add(gameElement);
			}

			cell.placeGameElement(gameElement);
		}

		// Run testing operation
		ArrayList<GameElement> gottenSewers = cell.getGameElements(Sewer.class);

		// Check needed state of entity ...
		// ... check that gotten robots are the same as created robots
		Assertions.assertEquals(sewers, gottenSewers);
		 */
	}
}
