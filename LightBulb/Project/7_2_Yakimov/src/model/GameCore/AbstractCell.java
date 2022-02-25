package model.GameCore;

import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractCell {
	// ============= ATTRIBUTES =============
	private ArrayList<GameElement>          _gameElements   = new ArrayList<>();                        // Inner game elements
	private Map<Direction, AbstractCell>    _neighbours     = new HashMap<Direction, AbstractCell>();   // All neighbours in given direction
	private Set<Direction>              	_walls      	= new HashSet<>();                    		// Walls

	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public AbstractCell() {}

	// --------- contract ---------
	// ~~~ game element ~~~
	/** Get all game elements with given type
	 * @param gameElementType - type of the game element
	 * @return array of game elements with given type
	 */
	public ArrayList<GameElement> getGameElements(@NotNull Class gameElementType) {
		ArrayList<GameElement> result = new ArrayList<>();

		for (var gameElement : this._gameElements) {
			if (gameElementType.isInstance(gameElement)) {
				result.add(gameElement);
			}
		}

		return result;
	}

	/** Place game element
	 * @param gameElement - given game element which will be placed
	 * @return success of placing game element on the cell
	 */
	public boolean placeGameElement(@NotNull GameElement gameElement) {
		boolean success;

		if (!this._gameElements.contains(gameElement)) {
			success = this.canPlace(gameElement);

			if (success) { success = gameElement.setPosition(this); }
			if (success) { success = this._gameElements.add(gameElement); }
		}
		else {
			success = true;
		}

		return success;
	}

	/** Check if cell can place given game element
	 * @param gameElement - game element which will be placed
	 * @return sign of possibility to place given game element
	 */
	public boolean canPlace(@NotNull GameElement gameElement) {
		// Check if given game element has type of some inner game element
		boolean success = !this.containsGameElementType(gameElement.getClass());

		// If given game element type is new to the cell
		if (success) {
			// Check if inner game elements can be placed with given game element
			for (var innerGameElement : this._gameElements) {
				success &= innerGameElement.canBeInSameCellWith(gameElement.getClass());

				// Stop checking if some inner game element can't be placed with given game element
				if (!success) { break; }
			}
		}

		return success;
	}

	/** Remove game element
	 * @param gameElement - given game element which will be removed
	 * @return success of removing game element from the cell
	 */
	public boolean removeGameElement(@NotNull GameElement gameElement) {
		boolean success;
		if (this._gameElements.contains(gameElement)) {
			success = gameElement.unsetPosition();
			success &= this._gameElements.remove(gameElement);
		}
		else {
			success = false;
		}

		return success;
	}

	/** Check if cell contains given game element's type
	 * @param gameElementType  - type of the game element
	 * @return sign of containing given game element's type
	 */
	public boolean containsGameElementType(@NotNull Class gameElementType) {
		ArrayList<GameElement> gameElementsWithGivenType = this.getGameElements(gameElementType);
		return !gameElementsWithGivenType.isEmpty();
	}

	// ~~~ neighbours ~~~
	/** Check if cell has neighbour in given direction
	 * @param direction - direction in which having neighbour will be checked
	 * @return sign of having neighbour in given direction
	 */
	public boolean hasNeighbour(@NotNull Direction direction) {
		return this._neighbours.containsKey(direction);
	}

	/** Get neighbour cell in given direction
	 * @param direction - direction in which neighbour cell will be gotten
	 * @return neighbour cell
	 */
	public AbstractCell neighbour(@NotNull Direction direction) {
		if (!this.hasNeighbour(direction)) {
			throw new RuntimeException("ERROR: try to get NON-EXISTENT neighbour.");
		}

		return this._neighbours.get(direction);
	}

	/** Get neighbours reachable by given robot
	 * @param robot - robot which will define which neighbours of cell he can reach
	 * @return neighbours reachable by given robot
	 */
	public final ArrayList<AbstractCell> neighboursReachableBy(@NotNull AbstractRobot robot) {
		ArrayList<AbstractCell> result = new ArrayList<>();

		if (robot.canMakeStep(this, Direction.UP)) 	{ result.add(this.neighbour(Direction.UP)); }
		if (robot.canMakeStep(this, Direction.DOWN)) 	{ result.add(this.neighbour(Direction.DOWN)); }
		if (robot.canMakeStep(this, Direction.RIGHT)) 	{ result.add(this.neighbour(Direction.RIGHT)); }
		if (robot.canMakeStep(this, Direction.LEFT)) 	{ result.add(this.neighbour(Direction.LEFT)); }

		return result;
	}

	/** Set neighbour in given direction
	 * @param direction - direction in which neighbour will be set
	 * @param cell 		- cell which will be set as neighbour
	 */
	void setNeighbour(@NotNull Direction direction, @NotNull AbstractCell cell) {
		boolean isNotYourself 				= (this != cell);
		boolean noSetNeighbourInDirection 	= !this.hasNeighbour(direction);
		boolean isNotANeighbourAlready 		= !this._neighbours.containsValue(cell);

		if (isNotYourself && noSetNeighbourInDirection && isNotANeighbourAlready) {
			this._neighbours.put(direction, cell);
			cell.setNeighbour(direction.opposite(), this);
		}
	}

	/** Check if given cell is neighbour
	 * @param cell		other cell
	 * @return sign indicating that given cell is neighbour
	 */
	public boolean isNeighbour(@NotNull AbstractCell cell) { return this._neighbours.containsValue(cell); }

	/** Get direction in which given neighbour cell is placed
	 * @param neighbour			neighbour cell
	 * @return direction in which given neighbour cell is placed
	 */
	@NotNull
	public Direction getDirectionOfNeighbourPlacement(@NotNull AbstractCell neighbour) {
		Direction result = null;

		for (var neighbourInfo : this._neighbours.entrySet()) {
			if (neighbourInfo.getValue() == neighbour) {
				result = neighbourInfo.getKey();
				break;
			}
		}

		return result;
	}

	// ~~~ walls ~~~
	/** Check if cell has wall in given direction
	 * @param direction - direction in which having wall will be checked
	 * @return sign of having wall in given direction
	 */
	public boolean hasWall(@NotNull Direction direction) {
		return this._walls.contains(direction);
	}

	/** Set wall
	 * @param direction - direction in which wall will be set
	 */
	boolean setWall(@NotNull Direction direction) {
		boolean success = !this.hasWall(direction);

		if (success) {
			this._walls.add(direction);

			if (this.hasNeighbour(direction)) {
				AbstractCell neighbour = this.neighbour(direction);
				neighbour.setWall(direction.opposite());
			}
		}

		return success;
	}
}
