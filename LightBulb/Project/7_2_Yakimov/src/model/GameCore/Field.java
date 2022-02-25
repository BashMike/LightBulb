package model.GameCore;

import model.Cells.StandardCell;
import model.GameCore.FinishingGameCore.ExitCell;
import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleArea;
import model.Navigation.RectangleSize;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Field {
	// ============= ATTRIBUTES =============
	// ~~~ main ~~~
	private RectangleArea					_fieldArea;													// Rectangle area of the field

	private Map<CellPosition, AbstractCell>	_allCells 	= new HashMap<CellPosition, AbstractCell>(); 	// Cells of the field

	// ~~~ inner elements ~~~
	private ExitCell 						_exitCell 	= null;											// Exit cell of the field
	private SmartRobot 						_smartRobot	= null;											// Smart robot
	private SillyRobot 						_sillyRobot	= null;											// Silly robot

	private	ArrayList<Landscape>			_landscapes	= new ArrayList<>();							// Landscapes

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public Field(MazeDesigner mazeDesigner) {
		// Init field ...
		// ... create cells from information from given maze designer
		this._createCells(mazeDesigner);
		// ... connect cells
		this._connectCells(mazeDesigner.fieldSize());

		// Fill field
		mazeDesigner.fillField(this);
		this._fieldArea = new RectangleArea(new CellPosition(0, 0), mazeDesigner.fieldSize());

		// Ask maze designer for needed created entities
		this._askMazeDesignerForCreatedRobots(mazeDesigner);
		this._landscapes = mazeDesigner.getCreatedLandscapes();
	}

	/** Create cells from information of maze designer
	 */
	private void _createCells(MazeDesigner mazeDesigner) {
		// Create cells
		RectangleSize fieldSize = mazeDesigner.fieldSize();

		for (int x=0; x<fieldSize.width(); x++) {
			for (int y=0; y<fieldSize.height(); y++) {
				this._allCells.put(new CellPosition(x, y), new StandardCell());
			}
		}

		// Place exit cell
		CellPosition exitCellPosition = mazeDesigner.exitCellPosition();
		this._exitCell = new ExitCell();
		this._allCells.replace(exitCellPosition, this._exitCell);
	}

	/** Connect all cells
	 * @param fieldSize - size of the field
	 */
	private void _connectCells(RectangleSize fieldSize) {
		for (int x=0; x<fieldSize.width(); x++) {
			for (int y=0; y<fieldSize.height(); y++) {
				AbstractCell cell = this._allCells.get(new CellPosition(x, y));

				// Try to set up neighbour
				if (y > 0) {
					AbstractCell neighbour = this._allCells.get(new CellPosition(x, y-1));
					cell.setNeighbour(Direction.UP, neighbour);
				}

				// Try to set down neighbour
				if (y < fieldSize.height()-1) {
					AbstractCell neighbour = this._allCells.get(new CellPosition(x, y+1));
					cell.setNeighbour(Direction.DOWN, neighbour);
				}

				// Try to set right neighbour
				if (x < fieldSize.width()-1) {
					AbstractCell neighbour = this._allCells.get(new CellPosition(x+1, y));
					cell.setNeighbour(Direction.RIGHT, neighbour);
				}

				// Try to set left neighbour
				if (x > 0) {
					AbstractCell neighbour = this._allCells.get(new CellPosition(x-1, y));
					cell.setNeighbour(Direction.LEFT, neighbour);
				}
			}
		}
	}

	/** Ask maze designer for created robots
	 * @param mazeDesigner - maze designer which know its created robots
	 */
	private void _askMazeDesignerForCreatedRobots(@NotNull MazeDesigner mazeDesigner) {
		ArrayList<AbstractRobot> createdRobots = mazeDesigner.getCreatedRobots();

		// Check that maze designer created only 2 robots (smart robot and silly robot)
		if (createdRobots.size() == 2) {
			boolean isFirstIsSmartRobot = createdRobots.get(0) instanceof SmartRobot;
			boolean isFirstIsSillyRobot = createdRobots.get(0) instanceof SillyRobot;

			boolean isSecondIsSmartRobot = createdRobots.get(1) instanceof SmartRobot;
			boolean isSecondIsSillyRobot = createdRobots.get(1) instanceof SillyRobot;

			// Set error if maze designer created 2 similar robots
			if (!((isFirstIsSmartRobot && isSecondIsSillyRobot) || (isFirstIsSillyRobot && isSecondIsSmartRobot))) {
				throw new RuntimeException("ERROR: maze designer created TWO SIMILAR ROBOTS.");
			}
		}
		else {
			// Set error if maze designer created more or less than 2 robots
			throw new RuntimeException("ERROR: maze designer created MORE OR LESS ROBOTS than 2 robots.");
		}

		// Get smart robot from created robots
		if (createdRobots.get(0) instanceof SmartRobot) 		{ this._smartRobot = (SmartRobot)createdRobots.get(0); }
		else if (createdRobots.get(1) instanceof SmartRobot)	{ this._smartRobot = (SmartRobot)createdRobots.get(1); }

		// Get silly robot from created robots
		if (createdRobots.get(0) instanceof SillyRobot) 		{ this._sillyRobot = (SillyRobot)createdRobots.get(0); }
		else if (createdRobots.get(1) instanceof SillyRobot)	{ this._sillyRobot = (SillyRobot)createdRobots.get(1); }
	}

	// -------- attributes --------
	/** Get rectangle area of the field
	 * @return rectangle area of the field
	 */
    public RectangleArea fieldArea() { return this._fieldArea; }

	// --------- contract ---------
	// ~~~ cells ~~~
	/** Check if field has cell at given position
	 * @param position - cell's position
	 * @return sign that field has a cell at given position
	 */
	public boolean hasCell(CellPosition position) { return this._allCells.containsKey(position); }

	/** Check if field has given cell
	 * @param cell - given cell
	 * @return sign that field has given cell
	 */
	public boolean hasCell(AbstractCell cell) { return this._allCells.containsValue(cell); }

	/** Get cell of the field at given position
	 * @param position  - position of the cell which will be received
	 * @return cell of the field
	 */
	public AbstractCell cell(@NotNull CellPosition position) {
		if (!this.hasCell(position)) {
			throw new RuntimeException("ERROR: try to get NON-EXISTENT position.");
		}

		return this._allCells.get(position);
	}

    /** Get cell position of given cell
     * @param cell - cell which position will be gotten
	 * @return cell position of given cell
     */
    @NotNull
    public CellPosition cellPosition(@NotNull AbstractCell cell) {
    	CellPosition result = null;

		for (var cellInfo : this._allCells.entrySet()) {
			if (cellInfo.getValue() == cell) {
				result = cellInfo.getKey();
				break;
			}
		}

    	return result;
	}

	/** Get cells of the field in the given rectangle area
	 * @param area - area from where cells of the field will be received
	 * @return cells of the field
	 */
	public Map<CellPosition, AbstractCell> cells(RectangleArea area) {
	    Map<CellPosition, AbstractCell> result = new HashMap<>();

	    // Find vertical and horizontal range of cells basing on given rectangle area
	    int xStart = area.leftTopPosition().x();
		int yStart = area.leftTopPosition().y();

		int xEnd = xStart + area.size().width() - 1;
		int yEnd = yStart + area.size().height() - 1;

		// Extract all existent cells of field from found vertical and horizontal range of cells
		for (int x=xStart; x<=xEnd; x++) {
			for (int y=yStart; y<=yEnd; y++) {
				if (this.hasCell(new CellPosition(x, y))) {
					result.put(new CellPosition(x, y), this.cell(new CellPosition(x, y)));
				}
			}
		}

		return result;
	}

	/** Check if field has cells
	 * @return sign that field has cells
	 */
	public boolean isEmpty() { return this._allCells.isEmpty(); }

	/** Get the shortest route in the field for given robot type
	 * @param robot 		- robot which will be used for defining reachable cells
	 * @param startCell 	- start cell of the route
	 * @param finishCell    - finish cell of the route
	 * @return route of cells
	 */
	public ArrayList<AbstractCell> findShortestRoute(@NotNull AbstractRobot robot, @NotNull AbstractCell startCell, @NotNull AbstractCell finishCell) {
		ArrayList<AbstractCell> result = new ArrayList<>();

		// Mark all cells beginning from start cell until all cells are marked or finish cell is marked
		Map<AbstractCell, Integer> markedCells = this._markFieldCellsToFinishCell(robot, startCell, finishCell);

        // Get result path moving from finish cell to start cell if finish cell was reached
		if (markedCells.containsKey(finishCell)) {
			result = this._movingThroughMarkedCellsFromStartToFinish(markedCells, finishCell);
		}

		return result;
	}

	/** Mark all cells beginning at start cell and ending at finish cell
	 * @param robot			- robot which will be used for defining reachable cells
	 * @param startCell		- start cell of the route
	 * @param finishCell	- finish cell of the route
	 * @return marked cells beginning at start cell and ending at finish cell
	 */
	private Map<AbstractCell, Integer> _markFieldCellsToFinishCell(@NotNull AbstractRobot robot, @NotNull AbstractCell startCell, @NotNull AbstractCell finishCell) {
		// Create result marked cells putting the start cell in it
		Map<AbstractCell, Integer> result = new HashMap<AbstractCell, Integer>();
		result.put(startCell, 0);

		// Init wave front of movement through field putting the start cell in it
		ArrayList<AbstractCell> waveFront = new ArrayList<>();

		ArrayList<AbstractCell> newWaveFront = new ArrayList<>();
		newWaveFront.add(startCell);

		// Move wave front marking all met cells until finish cell wasn't marked and wave front can be continued
		Integer currentMarkValue = 1; 		// Current mark value
		boolean isFinishCellMarked;			// Sign showing that finish cell is marked
		boolean canWaveFrontBeContinued;	// Sign showing that wave front can be continued
		do {
			// Continue current wave front to new found wave front
            waveFront = new ArrayList<>(newWaveFront);
			newWaveFront.clear();

			// Find new wave front
			for (var cell : waveFront) { // For each cell in current wave front
		    	ArrayList<AbstractCell> reachableNeighbours = cell.neighboursReachableBy(robot); // Get all neighbours of current cell reachable by given robot

				// Mark all unmarked neighbours
				for (var neighbour : reachableNeighbours) {
					if (!result.containsKey(neighbour)) {
						result.put(neighbour, currentMarkValue);
						newWaveFront.add(neighbour);
					}
				}
			}

            currentMarkValue++;

			// Check the end of wave front moving
            isFinishCellMarked = result.containsKey(finishCell);
			canWaveFrontBeContinued = !newWaveFront.isEmpty();
		} while (!isFinishCellMarked && canWaveFrontBeContinued);

		return result;
	}

	/** Make a route in marked cells from start cell to finish cell
	 * @param markedCells
	 * @param finishCell
	 * @return
	 */
	private ArrayList<AbstractCell> _movingThroughMarkedCellsFromStartToFinish(@NotNull Map<AbstractCell, Integer> markedCells, @NotNull AbstractCell finishCell) {
	    // Start result path with finish cell
	    ArrayList<AbstractCell> result = new ArrayList<>();
	    result.add(finishCell);

		AbstractCell currentCell = finishCell; 					// Set current cell as finish cell from where movement will be
		Integer currentMarkValue = markedCells.get(finishCell); // Set current mark value as finish cell's mark value

		// While start cell wasn't reached
	    while (currentMarkValue != 0) {
	    	// Get all neighbours of current cell
	    	ArrayList<AbstractCell> neighbours = new ArrayList<>();
	    	if (currentCell.hasNeighbour(Direction.UP)) 	{ neighbours.add(currentCell.neighbour(Direction.UP)); }
			if (currentCell.hasNeighbour(Direction.DOWN)) 	{ neighbours.add(currentCell.neighbour(Direction.DOWN)); }
			if (currentCell.hasNeighbour(Direction.RIGHT)) 	{ neighbours.add(currentCell.neighbour(Direction.RIGHT)); }
			if (currentCell.hasNeighbour(Direction.LEFT)) 	{ neighbours.add(currentCell.neighbour(Direction.LEFT)); }

			// For each neighbour cell
			for (var neighbour : neighbours) {
				// If neighbour cell is marked and its value is less than current mark value by 1
				if (markedCells.containsKey(neighbour) && (currentMarkValue - markedCells.get(neighbour) == 1)) {
					result.add(neighbour); 		// Add neighbour cell to result path
					currentCell = neighbour;	// Set current cell as added neighbour cell

					currentMarkValue--;			// Set current mark value as mark value of added neighbour cell

					break;						// Stop searching for appropriate neighbour cell
				}
			}
		}

		Collections.reverse(result); 	// Reverse gotten result path

	    return result;
	}

	// ~~~ game's needs ~~~
	/** Get smart robot
	 * @return smart robot
	 */
	public SmartRobot getSmartRobot() { return this._smartRobot; }

	/** Get silly robot
	 * @return silly robot
	 */
	public SillyRobot getSillyRobot() { return this._sillyRobot; }

	/** Get exit cell
	 * @return exit cell
	 */
	public ExitCell getExitCell() { return this._exitCell; }

	// ~~~ landscapes ~~~
	void updateLandscapes(@NotNull Game.Season season) {
		for (var landscape : this._landscapes) {
			if (landscape instanceof UpdatableLandscape) {
				((UpdatableLandscape)landscape).update(season);
			}
		}
	}
}