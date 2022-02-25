package model.GameCore;

import model.Additional.Pair;
import model.GameCore.FinishingGameCore.Sewer;
import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Landscapes.Sand;
import model.Navigation.BetweenCellsPosition;
import model.Navigation.CellPosition;
import model.Navigation.RectangleSize;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class MazeDesigner {
	// ============= ATTRIBUTES =============
	private RectangleSize 																	_fieldSize 					= null;					// Size of the field
	private CellPosition 																	_exitCellPosition			= null;					// Position	of the exit cell

	private ArrayList<BetweenCellsPosition>													_wallsInfo 					= new ArrayList<>();	// Walls' info needed for creating on the field
	private ArrayList<Pair<Class<? extends GameElement>, CellPosition>> 					_gameElementsInfo 			= new ArrayList<>();	// Game elements' info needed for creating on the field
    private ArrayList<Pair<Class<? extends SingleLandscape>, CellPosition>>					_singleLandscapesInfo 		= new ArrayList<>();	// Single landscapes' info needed for creating on the field
	private ArrayList<Pair<Class<? extends MultipleLandscape>, ArrayList<CellPosition>>>	_multipleLandscapesInfo 	= new ArrayList<>();	// Multiple landscapes' info needed for creating on the field

	private ArrayList<AbstractRobot>														_createdRobots 				= new ArrayList<>();	// Created robots on the field
    private ArrayList<Landscape>															_createdLandscapes			= new ArrayList<>();	// Create landscapes on the field

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public MazeDesigner() {}

	/** Set size of the field
	 * @param fieldSize 					size of the field
	 */
	protected void _setFieldSize(@NotNull RectangleSize fieldSize) {
		if (this._fieldSize != null) {
			throw new RuntimeException("ERROR: Try to SET field size AGAIN.");
		}

		this._fieldSize = fieldSize;
	}

	/** Set exit cell position
	 * @param exitCellPosition				position of the exit cell
	 */
	protected void _setExitCellPosition(@NotNull CellPosition exitCellPosition) {
		if (this._exitCellPosition != null) {
			throw new RuntimeException("ERROR: Try to SET exit cell position AGAIN.");
		}

		this._exitCellPosition = exitCellPosition;
	}

	/** Add wall information for creating on the field
	 * @param wallPosition 					position of the wall
	 */
	protected void _addWallInfo(@NotNull BetweenCellsPosition wallPosition) {
		if (this._wallsInfo.contains(wallPosition)) {
			throw new RuntimeException("ERROR: Try to ADD wall's info AGAIN.");
		}

		this._wallsInfo.add(wallPosition);
	}

	/** Add game element information for creating on the field
	 * @param gameElementType				type of game element
	 * @param gameElementPosition			position of game element
	 */
	protected void _addGameElementInfo(@NotNull Class<? extends GameElement> gameElementType, @NotNull CellPosition gameElementPosition) {
		Pair<Class<? extends GameElement>, CellPosition> gameElementInfo = new Pair<>(gameElementType, gameElementPosition);
		if (this._gameElementsInfo.contains(gameElementInfo)) {
			throw new RuntimeException("ERROR: Try to ADD game element's info AGAIN.");
		}

		this._gameElementsInfo.add(gameElementInfo);
	}

	/** Add single landscape information for creating on the field
	 * @param singleLandscapeType			type of single landscape
	 * @param singleLandscapePosition		position of single landscape
	 */
	protected void _addSingleLandscapeInfo(@NotNull Class<? extends SingleLandscape> singleLandscapeType, @NotNull CellPosition singleLandscapePosition) {
		Pair<Class<? extends SingleLandscape>, CellPosition> singleLandscapeInfo = new Pair<>(singleLandscapeType, singleLandscapePosition);
		if (this._singleLandscapesInfo.contains(singleLandscapeInfo)) {
			throw new RuntimeException("ERROR: Try to ADD single landscape's info AGAIN.");
		}

		this._singleLandscapesInfo.add(singleLandscapeInfo);
	}

	/** Add multiple landscape information for creating on the field
	 * @param multipleLandscapeType			type of multiple landscape
	 * @param multipleLandscapePositions	position of multiple landscape
	 */
	protected void _addMultipleLandscapeInfo(@NotNull Class<? extends MultipleLandscape> multipleLandscapeType, @NotNull ArrayList<CellPosition> multipleLandscapePositions) {
		Pair<Class<? extends MultipleLandscape>, ArrayList<CellPosition>> multipleLandscapeInfo = new Pair<>(multipleLandscapeType, multipleLandscapePositions);
		if (this._multipleLandscapesInfo.contains(multipleLandscapeInfo)) {
			throw new RuntimeException("ERROR: Try to ADD multiple landscape's info AGAIN.");
		}

		this._multipleLandscapesInfo.add(multipleLandscapeInfo);
	}

	// --------- contract ---------
	// ~~~ information for building field ~~~
	/** Get size of the field
	 * @return size of the field
	 */
	public RectangleSize fieldSize() {
		return this._fieldSize;
	}

	/** Get position of the exit cell
	 * @return position of the exit cell
	 */
	public CellPosition exitCellPosition() {
		return this._exitCellPosition;
	}

	// ~~~ fill field ~~~
	/** Fill the field with needed elements
	 * @param field							field which will be filled with needed elements
	 * @return sign of field filling's success
	 */
	public final boolean fillField(@NotNull Field field) {
	    boolean success;
	    success = this._fillFieldWithWalls(field);
		success &= this._fillFieldWithGameElements(field);
		success &= this._fillFieldWithSingleLandscapes(field);
		success &= this._fillFieldWithMultipleLandscapes(field);

		return success;
	}

	/** Get created robots
	 * @return created robots
	 */
	ArrayList<AbstractRobot> getCreatedRobots() {
		return this._createdRobots;
	}

	/** Get created landscapes
	 * @return created landscapes
	 */
	ArrayList<Landscape> getCreatedLandscapes() { return this._createdLandscapes; }

	/** Fill the field with walls
	 * @param field							field which will be filled with walls
	 * @return sign of field filling's success
	 */
	private boolean _fillFieldWithWalls(@NotNull Field field) {
		boolean success = false;

		for (var wallInfo : this._wallsInfo) {
		    if (!wallInfo.isNormalized()) {
		    	wallInfo = wallInfo.normalized();
			}

		    // Check if normalized between cells position is valid in context of given field
			if (field.hasCell(wallInfo.cellPosition())) {
				AbstractCell cell = field.cell(wallInfo.cellPosition());
				cell.setWall(wallInfo.direction());

				success = true;
			}
			// else check if denormalized between cells position is valid in context of given field
			else if (field.hasCell(wallInfo.denormalized().cellPosition())) {
			    wallInfo = wallInfo.denormalized();
				success = true;
			}

			if (success) {
				AbstractCell cell = field.cell(wallInfo.cellPosition());
				cell.setWall(wallInfo.direction());
			}
		}

		return success;
	}

	/** Fill the field with game elements
	 * @param field							field which will be filled with game elements
	 * @return sign of field filling's success
	 */
	private boolean _fillFieldWithGameElements(@NotNull Field field) {
		boolean success = false;

		// For each game element's info
		for (var gameElementInfo : this._gameElementsInfo) {
			// Create game element from its given info
			GameElement gameElement = this._createGameElementFromItsInfo(gameElementInfo.key(), field);
			if (gameElement != null && gameElement instanceof AbstractRobot) { this._createdRobots.add((AbstractRobot)gameElement); }

			// Try to place created game element if it's created successfully
			if (gameElement != null) {
				success |= this._placeCreatedGameElement(field, gameElement, gameElementInfo.value());
			}
		}

		return success;
	}

	/** Create game element from its info
	 * @param gameElementType 				type of game element from which game element will be created
	 * @param field							field needed for creating game elements which needs field info
	 * @return created game element
	 */
	private GameElement _createGameElementFromItsInfo(@NotNull Class<? extends GameElement> gameElementType, @NotNull Field field) {
	    GameElement result = null;

		if (gameElementType == SmartRobot.class) 		{ result = new SmartRobot(); }
		else if (gameElementType == SillyRobot.class) 	{ result = new SillyRobot(field); }

		return result;
	}

	/** Place created game element to the field at given cell position
	 * @param field							field in which created game element will be placed
	 * @param gameElement					game element which will be placed
	 * @param cellPosition					cell position at which created game element will be placed on field
	 * @return sign of successful placing on field
	 */
	private boolean _placeCreatedGameElement(@NotNull Field field, @NotNull GameElement gameElement, @NotNull CellPosition cellPosition) {
		boolean success = true;

		if (field.hasCell(cellPosition)) {
			AbstractCell cell = field.cell(cellPosition);
			success &= cell.placeGameElement(gameElement);
		}

		return success;
	}

	/** Fill the field with single landscapes
	 * @param field							field which will be filled with single landscapes
	 * @return sign of field filling's success
	 */
	private boolean _fillFieldWithSingleLandscapes(@NotNull Field field) {
		boolean success = true;

		// For each single landscape's info
		for (var singleLandscapeInfo : this._singleLandscapesInfo) {
			// Create single landscape from its given info
			SingleLandscape singleLandscape = this._createSingleLandscapeFromItsInfo(singleLandscapeInfo.key());

			// Try to place created single landscape if it's created successfully
			if (singleLandscape != null) {
				this._createdLandscapes.add(singleLandscape);
				success &= this._placeCreatedGameElement(field, singleLandscape, singleLandscapeInfo.value());
			}
			else {
				success = false;
			}
		}

		return success;
	}

	/** Create single landscape from its info
	 * @param singleLandscapeType 			type of single landscape from which single landscape will be created
	 * @return created single landscape
	 */
	@NotNull
	private SingleLandscape _createSingleLandscapeFromItsInfo(@NotNull Class<? extends SingleLandscape> singleLandscapeType) {
		SingleLandscape result = null;

		if (singleLandscapeType == Sand.class) { result = new Sand(); }

		return result;
	}

	/** Fill the field with multiple landscapes
	 * @param field			field which will be filled with multiple landscapes
	 * @return sign of field filling's success
	 */
	private boolean _fillFieldWithMultipleLandscapes(@NotNull Field field) {
		boolean success = true;

		// For each multiple landscape's info
		for (var multipleLandscapeInfo : this._multipleLandscapesInfo) {
			// Create multiple landscape from its given info
			MultipleLandscape multipleLandscape = this._createMultipleLandscapeFromItsInfo(multipleLandscapeInfo.key());

			// Try to place created multiple landscape if it's created successfully
			if (multipleLandscape != null) {
				this._createdLandscapes.add(multipleLandscape);
				success &= this._placeCreatedMultipleLandscape(field, multipleLandscape, multipleLandscapeInfo.value());
			}
			else {
				success = false;
			}
		}

		return success;
	}

	/** Create multiple landscape from its info
	 * @param multipleLandscapeType 	type of multiple landscape from which multiple landscape will be created
	 * @return created multiple landscape
	 */
	@NotNull
	private MultipleLandscape _createMultipleLandscapeFromItsInfo(@NotNull Class<? extends MultipleLandscape> multipleLandscapeType) {
		MultipleLandscape result = null;

		if (multipleLandscapeType == Sewer.class) { result = new Sewer(); }

		return result;
	}

	/** Place created multiple landscape to the field at given cell positions
	 * @param field						field in which created game element will be placed
	 * @param multipleLandscape			game element which will be placed
	 * @param elementsCellPositions		cell position at which created game element will be placed on field
	 * @return
	 */
	private boolean _placeCreatedMultipleLandscape(@NotNull Field field, @NotNull MultipleLandscape multipleLandscape, @NotNull ArrayList<CellPosition> elementsCellPositions) {
		boolean success = true;

		for (CellPosition elementCellPosition : elementsCellPositions) {
			if (field.hasCell(elementCellPosition)) {
				AbstractCell cell = field.cell(elementCellPosition);
				success &= multipleLandscape.fillCell(cell);
			}
		}

		return success;
	}
}