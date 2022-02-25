package model.GameCore;

import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

public final class LandscapeActions {
    // ============= ATTRIBUTES =============
    // ~~~ main ~~~
    private Landscape _owner; // Owner of landscape action

    // ============= OPERATIONS =============
    // ---------- create ----------
    /** Constructor
     * @param owner         owner of landscape action
     */
    LandscapeActions(@NotNull Landscape owner) {
        this._owner = owner;
    }

    // --------- contract ---------
    /** Make robot skip moves
     * @param robot             robot which will skip moves
     * @param skipMovesCount    count of moves which will be skipped
     */
    public void makeRobotSkipMoves(@NotNull AbstractRobot robot, int skipMovesCount) {
        robot.skipMoves(skipMovesCount);

        if (this._owner instanceof LandscapeWithMultipleStates) {
            robot.fireRobotIsAffectedByLandscape(this._owner.getClass(), ((LandscapeWithMultipleStates)this._owner).state());
        }
        else {
            robot.fireRobotIsAffectedByLandscape(this._owner.getClass(), null);
        }
    }

    /** Make robot move in given direction
     * @param robot         robot which will be moved in given direction
     * @param direction     direction in which robot will be moved
     * @param movesCount    count of robot's moves
     */
    public void makeRobotMove(@NotNull AbstractRobot robot, @NotNull Direction direction, int movesCount) {
        robot.move(direction, movesCount);

        if (this._owner instanceof LandscapeWithMultipleStates) {
            robot.fireRobotIsAffectedByLandscape(this._owner.getClass(), ((LandscapeWithMultipleStates)this._owner).state());
        }
        else {
            robot.fireRobotIsAffectedByLandscape(this._owner.getClass(), null);
        }
    }
}
