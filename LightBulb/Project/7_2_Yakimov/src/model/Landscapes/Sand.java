package model.Landscapes;

import model.GameCore.AbstractRobot;
import model.GameCore.SingleLandscape;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

public class Sand extends SingleLandscape {
    // ============= OPERATIONS =============
    // --------- contract ---------
    // ~~~ main ~~~
    /** Perform action on robot which has stepped on landscape
     * @param robot         robot which has stepped on landscape
     */
    public void performActionOnSteppingRobot(@NotNull AbstractRobot robot) {
        this._actions.makeRobotSkipMoves(robot, 1);
    }

    /** DOESN'T PERFORM ANY ACTION ON ROBOT
     * Perform action on robot which has moved from landscape
     * @param robot         robot which has moved from landscape
     */
    public void performActionOnMovingRobot(@NotNull AbstractRobot robot, @NotNull Direction direction) {}
}
