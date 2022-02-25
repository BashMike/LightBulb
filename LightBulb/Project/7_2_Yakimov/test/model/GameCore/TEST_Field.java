package model.GameCore;

import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TEST_Field {
	// ============ TEST PART ============
	// --- TESTS SHORT DESCRIPTION ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.
	//
	// 2. Cells;
    // 2.1. Get cell;
	// 2.1.1. Get cell in the field's area;
	// 2.1.2. Get cell in the edge of field's area;
	// 2.1.3. Get cell in the corner of field's area;
	// 2.1.4. Get cell out of the field's area.
	// 2.2. Get cells from given area.
	// 2.2.1. Get cells from area inside field's area;
	// 2.2.2. Get cells from area that covers the edge of field's area;
	// 2.2.3. Get cells from area that covers the corner of field's area;
    // 2.2.4. Get all cells of field's area;
	// 2.2.5. Get cells from area that corner is out of field's area;
	// 2.2.6. Get cells from area that edge is out of field's area;
	// 2.2.7. Get cells from area that is out of field's area.
	// 2.2.8. Get more cells than field's area have
	//
    // 3. Get the shortest route.
	// 3.1. Get the shortest route for smart robot;
	// 3.1.1. Simple route;
	// 3.1.2. Route with one obstacle;
	// 3.1.3. Route with many obstacles;
	// 3.1.4. Unreachable route;
	// 3.1.5. Route to obstacle;
	// 3.1.6. Route to silly robot;
	// 3.1.7. Route to smart robot;
	// 3.1.8. Route through exit cell
	// 3.2. Get the shortest route for smart robot.
	// 3.2.1. Simple route;
	// 3.2.2. Route with one obstacle;
	// 3.2.3. Route with many obstacles;
	// 3.2.4. Unreachable route;
	// 3.2.5. Route to obstacle;
	// 3.2.6. Route to smart robot;
	// 3.2.7. Route to silly robot.

	// ========== CREATION ==========
	// Init test maze designer for testing getting cell at given position or cells from given area
	private class ForTestMazeDesigner_creation extends MazeDesigner {
		public ForTestMazeDesigner_creation() {
			this._setFieldSize(new RectangleSize(2, 1));
			this._setExitCellPosition(new CellPosition(1, 0));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(0, 0));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 0));
		}
	}

	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		// Create initial state of entity
		Field field = new Field(new ForTestMazeDesigner_creation());

		// Run testing position AND check needed state of entity ...
		// ... check that field has cells
		Assertions.assertFalse(field.isEmpty());
	}

	// ========== CELLS ==========
	// Init test maze designer for testing getting cell at given position or cells from given area
	private class ForTestMazeDesigner_getCellOrCells extends MazeDesigner {
		public ForTestMazeDesigner_getCellOrCells() {
			this._setFieldSize(new RectangleSize(5, 5));
			this._setExitCellPosition(new CellPosition(2, 2));

			this._addGameElementInfo(SmartRobot.class, new CellPosition(3, 2));
			this._addGameElementInfo(SillyRobot.class, new CellPosition(1, 2));
		}
	}

	// --- GET CELL ---
	// get cell IN the field's AREA
	@Test
	void cell_inFieldArea() {
		// Create initial state of entity
		Field field = new Field(new ForTestMazeDesigner_getCellOrCells());

		// Run testing operation
		CellPosition cellPosition = new CellPosition(1, 2);

		AbstractCell cell = null;
		if (field.hasCell(cellPosition)) { cell = field.cell(cellPosition); }

		// Check needed state of entity ...
		// ... check that cell was gotten
		Assertions.assertNotNull(cell);
		// ... check that field has gotten cell and its position equals initial cell position
		Assertions.assertTrue(field.hasCell(cell));
		Assertions.assertEquals(cellPosition, field.cellPosition(cell));
	}

	// get cell IN THE EDGE of field's area
	@Test
	void cell_edgeOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition cellPosition = new CellPosition(mazeDesigner.fieldSize().width()-1, 2);

		AbstractCell cell = null;
		if (field.hasCell(cellPosition)) { cell = field.cell(cellPosition); }

		// Check needed state of entity ...
		// ... check that cell was gotten
		Assertions.assertNotNull(cell);
		// ... check that field has gotten cell and its position equals initial cell position
		Assertions.assertTrue(field.hasCell(cell));
		Assertions.assertEquals(cellPosition, field.cellPosition(cell));
	}

	// get cell IN THE CORNER of field's area
	@Test
	void cell_cornerOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition cellPosition = new CellPosition(mazeDesigner.fieldSize().width()-1, 0);

		AbstractCell cell = null;
		if (field.hasCell(cellPosition)) { cell = field.cell(cellPosition); }

		// Check needed state of entity ...
		// ... check that cell was gotten
		Assertions.assertNotNull(cell);
		// ... check that field has gotten cell and its position equals initial cell position
		Assertions.assertTrue(field.hasCell(cell));
		Assertions.assertEquals(cellPosition, field.cellPosition(cell));
	}

	// get cell OUT OF field's AREA
	@Test
	void cell_outOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition cellPosition = new CellPosition(mazeDesigner.fieldSize().width(), 0);
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()->field.cell(cellPosition));

		// Check needed state of entity ...
		// ... check that trying to get cell cause the exception
		Assertions.assertNotEquals("", exception.getMessage());
	}

	// --- GET CELLS IN GIVEN AREA ---
	// get cells from AREA that is INSIDE the field's AREA
	@Test
	void cells_areaInsideFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(2, 2);
		RectangleSize areaSize = new RectangleSize(2, 2);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
        Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=2; xStart<=3; xStart++) {
			for (int yStart=2; yStart<=3; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
            }
		}

		Assertions.assertEquals(4, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get cells from AREA that COVERS THE EDGE OF field's AREA
	@Test
	void cells_areaCoversFieldAreaEdge() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(0, 2);
		RectangleSize areaSize = new RectangleSize(1, 2);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=0; xStart<=0; xStart++) {
			for (int yStart=2; yStart<=3; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(2, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get cells from AREA that COVERS THE CORNER OF field's AREA
	@Test
	void cells_areaCoversFieldAreaCorner() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		RectangleSize fieldSize = mazeDesigner.fieldSize();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(fieldSize.width()-2, fieldSize.height()-2);
		RectangleSize areaSize = new RectangleSize(2, 2);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=fieldSize.width()-2; xStart<=fieldSize.width()-1; xStart++) {
			for (int yStart=fieldSize.height()-2; yStart<=fieldSize.height()-1; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(4, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get ALL CELLS from field's area
	@Test
	void cells_allCellsOfField() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		RectangleSize fieldSize = mazeDesigner.fieldSize();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(0, 0);
		RectangleSize areaSize = fieldSize;

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=0; xStart<=fieldSize.width()-1; xStart++) {
			for (int yStart=0; yStart<=fieldSize.height()-1; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(25, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get cells from AREA which CORNER IS OUT OF field's AREA
	@Test
	void cells_areaCornerIsOutOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(-1, -1);
		RectangleSize areaSize = new RectangleSize(2, 2);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=0; xStart<=0; xStart++) {
			for (int yStart=0; yStart<=0; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(1, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get cells from AREA which EDGE IS OUT OF field's AREA
	@Test
	void cells_areaEdgeIsOutOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(-2, 0);
		RectangleSize areaSize = new RectangleSize(4, 4);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=0; xStart<=1; xStart++) {
			for (int yStart=0; yStart<=3; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(8, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get cells from AREA that IS OUT OF field's AREA
	@Test
	void cells_areaIsOutOfFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(-2, 0);
		RectangleSize areaSize = new RectangleSize(2, 2);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();

		Assertions.assertTrue(expectedCells.isEmpty());
		Assertions.assertEquals(expectedCells, cells);
	}

	// get MORE CELLS THAN FIELD'S AREA have
	@Test
	void cells_areaIsBiggerThanFieldArea() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getCellOrCells();
		RectangleSize fieldSize = mazeDesigner.fieldSize();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		CellPosition leftTopPosition = new CellPosition(-2, -2);
		RectangleSize areaSize = new RectangleSize(fieldSize.width()+4, fieldSize.height()+4);

		RectangleArea receivedCellsArea = new RectangleArea(leftTopPosition, areaSize);
		Map<CellPosition, AbstractCell> cells = field.cells(receivedCellsArea);

		// Check needed state of entity ...
		// ... check that gotten cells equals expected cells
		Map<CellPosition, AbstractCell> expectedCells = new HashMap<CellPosition, AbstractCell>();
		for (int xStart=0; xStart<=fieldSize.width()-1; xStart++) {
			for (int yStart=0; yStart<=fieldSize.height()-1; yStart++) {
				if (field.hasCell(new CellPosition(xStart, yStart))) {
					expectedCells.put(new CellPosition(xStart, yStart), field.cell(new CellPosition(xStart, yStart)));
				}
			}
		}

		Assertions.assertEquals(25, expectedCells.size());
		Assertions.assertEquals(expectedCells, cells);
	}

	// ==== GET THE SHORTEST ROUTE ===
	// --- GET THE SHORTEST ROUTE FOR SMART ROBOT ---
	// Init test maze designer for testing getting cell at given position or cells from given area
	private class ForTestMazeDesigner_getShortestRoute extends MazeDesigner {
		public ForTestMazeDesigner_getShortestRoute() {
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

	// SIMPLE ROUTE for smart robot
	@Test
	void findShortestRoute_simpleForSmartRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(0, 0)) ? field.cell(new CellPosition(0, 0)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(2, 0)) ? field.cell(new CellPosition(2, 0)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(0, 0))) { expectedRoute.add(field.cell(new CellPosition(0, 0))); }
		if (field.hasCell(new CellPosition(1, 0))) { expectedRoute.add(field.cell(new CellPosition(1, 0))); }
		if (field.hasCell(new CellPosition(2, 0))) { expectedRoute.add(field.cell(new CellPosition(2, 0))); }

		Assertions.assertEquals(3, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// ONE OBSTACLE in front of smart robot
	@Test
	void findShortestRoute_oneObstacleForSmartRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 1)) ? field.cell(new CellPosition(1, 1)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(3, 1)) ? field.cell(new CellPosition(3, 1)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(1, 1))) { expectedRoute.add(field.cell(new CellPosition(1, 1))); }
		if (field.hasCell(new CellPosition(1, 0))) { expectedRoute.add(field.cell(new CellPosition(1, 0))); }
		if (field.hasCell(new CellPosition(2, 0))) { expectedRoute.add(field.cell(new CellPosition(2, 0))); }
		if (field.hasCell(new CellPosition(3, 0))) { expectedRoute.add(field.cell(new CellPosition(3, 0))); }
		if (field.hasCell(new CellPosition(3, 1))) { expectedRoute.add(field.cell(new CellPosition(3, 1))); }

		Assertions.assertEquals(5, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// A LOT OF OBSTACLES in front of smart robot
	@Test
	void findShortestRoute_manyObstaclesForSmartRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 2)) ? field.cell(new CellPosition(1, 2)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(3, 2)) ? field.cell(new CellPosition(3, 2)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(1, 2))) { expectedRoute.add(field.cell(new CellPosition(1, 2))); }
		if (field.hasCell(new CellPosition(1, 1))) { expectedRoute.add(field.cell(new CellPosition(1, 1))); }
		if (field.hasCell(new CellPosition(1, 0))) { expectedRoute.add(field.cell(new CellPosition(1, 0))); }
		if (field.hasCell(new CellPosition(2, 0))) { expectedRoute.add(field.cell(new CellPosition(2, 0))); }
		if (field.hasCell(new CellPosition(3, 0))) { expectedRoute.add(field.cell(new CellPosition(3, 0))); }
		if (field.hasCell(new CellPosition(3, 1))) { expectedRoute.add(field.cell(new CellPosition(3, 1))); }
		if (field.hasCell(new CellPosition(3, 2))) { expectedRoute.add(field.cell(new CellPosition(3, 2))); }

		Assertions.assertEquals(7, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// SMART ROBOT CAN'T REACH the finish cell
	@Test
	void findShortestRoute_smartRobotCantReach() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(0, 2)) ? field.cell(new CellPosition(0, 2)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(1, 2)) ? field.cell(new CellPosition(1, 2)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();

		Assertions.assertEquals(expectedRoute, route);
	}

	// smart robot's ROUTE TO the SEWER
	@Test
	void findShortestRoute_smartRobotsRouteToSewer() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 2)) ? field.cell(new CellPosition(1, 2)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(2, 2)) ? field.cell(new CellPosition(2, 2)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();

		Assertions.assertEquals(expectedRoute, route);
	}

	// SMART ROBOT's route TO the other SILLY ROBOT
	@Test
	void findShortestRoute_smartRobotsRouteToSillyRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(3, 3)) ? field.cell(new CellPosition(3, 3)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(4, 3)) ? field.cell(new CellPosition(4, 3)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(3, 3))) { expectedRoute.add(field.cell(new CellPosition(3, 3))); }
		if (field.hasCell(new CellPosition(4, 3))) { expectedRoute.add(field.cell(new CellPosition(4, 3))); }

		Assertions.assertEquals(2, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// SMART ROBOT's route TO the other SMART ROBOT
	@Test
	void findShortestRoute_smartRobotsRouteToSmartRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(3, 1)) ? field.cell(new CellPosition(3, 1)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(4, 1)) ? field.cell(new CellPosition(4, 1)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();

		Assertions.assertEquals(expectedRoute, route);
	}

	// SMART ROBOT's route THROUGH EXIT CELL
	@Test
	void findShortestRoute_smartRobotsRouteThroughExitCell() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(3, 1)) ? field.cell(new CellPosition(3, 1)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(3, 3)) ? field.cell(new CellPosition(3, 3)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SmartRobot(), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(3, 1))) { expectedRoute.add(field.cell(new CellPosition(3, 1))); }
		if (field.hasCell(new CellPosition(3, 2))) { expectedRoute.add(field.cell(new CellPosition(3, 2))); }
		if (field.hasCell(new CellPosition(3, 3))) { expectedRoute.add(field.cell(new CellPosition(3, 3))); }

		Assertions.assertEquals(3, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// --- GET THE SHORTEST ROUTE FOR SILLY ROBOT ---
	// SIMPLE route for silly robot
	@Test
	void findShortestRoute_simpleForSillyRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(0, 0)) ? field.cell(new CellPosition(0, 0)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(2, 0)) ? field.cell(new CellPosition(2, 0)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(0, 0))) { expectedRoute.add(field.cell(new CellPosition(0, 0))); }
		if (field.hasCell(new CellPosition(1, 0))) { expectedRoute.add(field.cell(new CellPosition(1, 0))); }
		if (field.hasCell(new CellPosition(2, 0))) { expectedRoute.add(field.cell(new CellPosition(2, 0))); }

		Assertions.assertEquals(3, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// ONE OBSTACLE in front of silly robot
	@Test
	void findShortestRoute_oneObstacleForSillyRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 1)) ? field.cell(new CellPosition(1, 1)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(3, 1)) ? field.cell(new CellPosition(3, 1)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(1, 1))) { expectedRoute.add(field.cell(new CellPosition(1, 1))); }
		if (field.hasCell(new CellPosition(1, 0))) { expectedRoute.add(field.cell(new CellPosition(1, 0))); }
		if (field.hasCell(new CellPosition(2, 0))) { expectedRoute.add(field.cell(new CellPosition(2, 0))); }
		if (field.hasCell(new CellPosition(3, 0))) { expectedRoute.add(field.cell(new CellPosition(3, 0))); }
		if (field.hasCell(new CellPosition(3, 1))) { expectedRoute.add(field.cell(new CellPosition(3, 1))); }

		Assertions.assertEquals(5, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// A LOT OF OBSTACLES in front of silly robot
	@Test
	void findShortestRoute_manyObstaclesForSillyRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 4)) ? field.cell(new CellPosition(1, 4)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(2, 4)) ? field.cell(new CellPosition(2, 4)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(1, 4))) { expectedRoute.add(field.cell(new CellPosition(1, 4))); }
		if (field.hasCell(new CellPosition(1, 3))) { expectedRoute.add(field.cell(new CellPosition(1, 3))); }
		if (field.hasCell(new CellPosition(1, 2))) { expectedRoute.add(field.cell(new CellPosition(1, 2))); }
		if (field.hasCell(new CellPosition(2, 2))) { expectedRoute.add(field.cell(new CellPosition(2, 2))); }
		if (field.hasCell(new CellPosition(2, 3))) { expectedRoute.add(field.cell(new CellPosition(2, 3))); }
		if (field.hasCell(new CellPosition(2, 4))) { expectedRoute.add(field.cell(new CellPosition(2, 4))); }

		Assertions.assertEquals(6, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// SILLY ROBOT CAN'T REACH the finish cell
	@Test
	void findShortestRoute_sillyRobotCantReach() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(0, 2)) ? field.cell(new CellPosition(0, 2)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(1, 2)) ? field.cell(new CellPosition(1, 2)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();

		Assertions.assertEquals(expectedRoute, route);
	}

	// SILLY robot's ROUTE TO the SEWER
	@Test
	void findShortestRoute_sillyRobotsRouteToSewer() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(1, 2)) ? field.cell(new CellPosition(1, 2)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(2, 2)) ? field.cell(new CellPosition(2, 2)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(1, 2))) { expectedRoute.add(field.cell(new CellPosition(1, 2))); }
		if (field.hasCell(new CellPosition(2, 2))) { expectedRoute.add(field.cell(new CellPosition(2, 2))); }

		Assertions.assertEquals(2, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// SILLY ROBOT's route TO the other SMART ROBOT
	@Test
	void findShortestRoute_sillyRobotsRouteToSmartRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(3, 1)) ? field.cell(new CellPosition(3, 1)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(4, 1)) ? field.cell(new CellPosition(4, 1)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();
		if (field.hasCell(new CellPosition(3, 1))) { expectedRoute.add(field.cell(new CellPosition(3, 1))); }
		if (field.hasCell(new CellPosition(4, 1))) { expectedRoute.add(field.cell(new CellPosition(4, 1))); }

		Assertions.assertEquals(2, expectedRoute.size());
		Assertions.assertEquals(expectedRoute, route);
	}

	// SILLY ROBOT's route TO the other SILLY ROBOT
	@Test
	void findShortestRoute_sillyRobotsRouteToSillyRobot() {
		// Create initial state of entity
		MazeDesigner mazeDesigner = new ForTestMazeDesigner_getShortestRoute();
		Field field = new Field(mazeDesigner);

		// Run testing operation
		AbstractCell startCell 	= (field.hasCell(new CellPosition(3, 3)) ? field.cell(new CellPosition(3, 3)) : null);
		AbstractCell finishCell = (field.hasCell(new CellPosition(4, 3)) ? field.cell(new CellPosition(4, 3)) : null);

		ArrayList<AbstractCell> route = new ArrayList<>();
		if (startCell != null && finishCell != null) {
			route = field.findShortestRoute(new SillyRobot(field), startCell, finishCell);
		}

		// Check needed state of entity ...
		// ... check that start and finish cells was successfully gotten from field
		Assertions.assertNotNull(startCell);
		Assertions.assertNotNull(finishCell);
		// ... check that route was successfully found
		ArrayList<AbstractCell> expectedRoute = new ArrayList<>();

		Assertions.assertEquals(expectedRoute, route);
	}
}