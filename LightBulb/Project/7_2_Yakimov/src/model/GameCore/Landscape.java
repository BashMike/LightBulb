package model.GameCore;

import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

public interface Landscape {
    // ============= OPERATIONS =============
    // --------- contract ---------
    /** Perform action on robot which has stepped on landscape
     * @param robot         robot which has stepped on landscape
     */
    void performActionOnSteppingRobot(@NotNull AbstractRobot robot);

    /** Perform action on robot which has moved from landscape
     * @param robot         robot which has moved from landscape
     * @param direction     direction in which robot has moved
     */
    void performActionOnMovingRobot(@NotNull AbstractRobot robot, @NotNull Direction direction);
}
