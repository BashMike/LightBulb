package model.GameCore.FinishingGameCore;

import model.GameCore.*;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Sewer extends MultipleLandscape implements LandscapeWithMultipleStates {
    // ============ INNER CLASSES ===========
    // State of sewer
    private enum SewerState implements LandscapeState {
        LIQUID,
        FROZEN
    }

    // ============= ATTRIBUTES =============
    // ~~~ main ~~~
    private SewerState                  _state                  = SewerState.LIQUID; // State of sewer
    private ArrayList<SingleLandscape>  _expandedInnerElements  = new ArrayList<>(); // Expanded part of inner elements

    // ============= OPERATIONS =============
    // --------- creating ---------
    /** Basic constructor
     */
    public Sewer() {}

    // --------- contract ---------
    // ~~~ main ~~~
    /** Perform action on robot which has stepped on landscape
     * @param robot         robot which has stepped on landscape
     */
    @Override
    public void performActionOnSteppingRobot(@NotNull AbstractRobot robot) {
        if (robot instanceof SmartRobot) {
            ((SmartRobot)robot).destroySelf();
            return;
        }

        if (this._state == SewerState.LIQUID) {
            this._actions.makeRobotSkipMoves(robot, 3);
        }
    }

    /** Perform action on robot which has moved from landscape
     * @param robot         robot which has moved from landscape
     */
    @Override
    public void performActionOnMovingRobot(@NotNull AbstractRobot robot, @NotNull Direction direction) {
        if (this._state == SewerState.FROZEN) {
            this._actions.makeRobotMove(robot, direction, 2);
        }
    }

    // ~~~ update ~~~
    @Override
    public void update(@NotNull Game.Season season) {
        switch(season) {
            case WINTER:    this._state = SewerState.FROZEN; break;
            case SPRING:    this._state = SewerState.LIQUID; break;
            case SUMMER:    this._returnToInitialSize(); break;
            case AUTUMN:    this._expand(); break;
        }
    }

    /** Expand self on the field
     */
    private void _expand() {
        // For each inner element
        for (var innerElement : this._innerElements) {
            // Get neighbour-cells of inner element where sewer can expand self
            ArrayList<AbstractCell> neighbourCells = new ArrayList<>();

            if (innerElement.position().hasNeighbour(Direction.UP) && !innerElement.position().hasWall(Direction.UP)) {
                neighbourCells.add(innerElement.position().neighbour(Direction.UP));
            }
            else if (innerElement.position().hasNeighbour(Direction.DOWN) && !innerElement.position().hasWall(Direction.DOWN)) {
                neighbourCells.add(innerElement.position().neighbour(Direction.DOWN));
            }
            else if (innerElement.position().hasNeighbour(Direction.RIGHT) && !innerElement.position().hasWall(Direction.RIGHT)) {
                neighbourCells.add(innerElement.position().neighbour(Direction.RIGHT));
            }
            else if (innerElement.position().hasNeighbour(Direction.LEFT) && !innerElement.position().hasWall(Direction.LEFT)) {
                neighbourCells.add(innerElement.position().neighbour(Direction.LEFT));
            }

            // Expand sewer on gotten neighbour-cells
            for (var neighbourCell : neighbourCells) {
                SingleLandscape expandedInnerElement = this._createAndPlaceInnerElement(neighbourCell);
                this._expandedInnerElements.add(expandedInnerElement);
            }
        }
    }

    /** Return to the initial size
     */
    private void _returnToInitialSize() {
        // Remove expanded inner elements from cells of the field
        for (var expandedInnerElement : this._expandedInnerElements) {
            expandedInnerElement.position().removeGameElement(expandedInnerElement);
        }

        // Forget expanded inner elements
        this._expandedInnerElements.clear();
    }

    /** Get state of sewer
     * @return state of sewer
     */
    public LandscapeState state() { return this._state; }
}
