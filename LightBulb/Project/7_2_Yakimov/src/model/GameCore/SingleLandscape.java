package model.GameCore;

import model.GameCore.FinishingGameCore.ExitCell;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SingleLandscape extends GameElement implements Landscape {
    // ============= ATTRIBUTES =============
    // ~~~ main ~~~
    protected LandscapeActions  _actions    = new LandscapeActions(this);     // All possible landscape actions
    private MultipleLandscape   _owner      = null;                                 // Owner of single landscape

    // ============= OPERATIONS =============
    // ---------- create ----------
    /** Basic constructor
     */
    public SingleLandscape() {}

    /** Constructor
     * @param owner         owner of single landscape
     */
    SingleLandscape(@NotNull MultipleLandscape owner) { this._owner = owner; }

    // -------- attributes --------
    /** Get type of the landscape
     * @return type of the landscape
     */
    public Class type() {
        Class result;

        // Return "Class" type of the single landscape based on presence of the owner
        if (this._owner != null)    { result = this._owner.getClass(); }
        else                        { result = this.getClass(); }

        return result;
    }

    // --------- contract ---------
    // ~~~ main ~~~
    /** Perform action on robot which has stepped on landscape
     * @param robot         robot which has stepped on landscape
     */
    @Override
    public void performActionOnSteppingRobot(@NotNull AbstractRobot robot) {
        if (this._owner != null) {
            this._owner.performActionOnSteppingRobot(robot);
        }
        else {
            throw new RuntimeException("ERROR: Try to perform action on robot which has stepped on landscape WITHOUT OWNER.");
        }
    }

    /** Perform action on robot which has moved from landscape
     * @param robot         robot which has moved from landscape
     */
    @Override
    public void performActionOnMovingRobot(@NotNull AbstractRobot robot, @NotNull Direction direction) {
        if (this._owner != null) {
            this._owner.performActionOnMovingRobot(robot, direction);
        }
        else {
            throw new RuntimeException("ERROR: Try to perform action on robot which has moved from landscape WITHOUT OWNER.");
        }
    }

    /** Perform action on all robots in personal cell the same as they would stepped on the landscape
     */
    final void performActionOnAllRobotsInCell() {
        // If single landscape has position
        if (this.hasPosition()) {
            // Get all robots from landscape's position
            ArrayList<GameElement> robots = this.position().getGameElements(AbstractRobot.class);

            // Perform action on each gotten robot
            for (GameElement robot : robots) {
                ((AbstractRobot)robot).stopBeingAffectedByLandscape();
                this.performActionOnSteppingRobot((AbstractRobot)robot);
            }
        }
    }

    // ~~~ position ~~~
    /** Set single landscape's position
     * @param cell - set position
     * @return sign of successful setting position
     */
    @Override
    final boolean setPosition(@NotNull AbstractCell cell) {
        // Set position
        boolean success = super.setPosition(cell);

        // Perform action on all robots in cell
        this.performActionOnAllRobotsInCell();

        return success;
    }

    /** Check if single landscape can set position
     * @param cell		cell which will be checked on the possibility of taking the position by single landscape
     * @return sign of successful possibility of taking the position by single landscape
     */
    protected boolean _canSetPosition(@NotNull AbstractCell cell) { return !(cell instanceof ExitCell); }

    /** Check that single landscape can be in the same cell with given single landscape type
     * @param gameElementType - type of other single landscape
     * @return sign of possibility being in the same with given single landscape type
     */
    @Override
    public boolean canBeInSameCellWith(@NotNull Class gameElementType) { return !SingleLandscape.class.isAssignableFrom(gameElementType); }
}