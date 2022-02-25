package model.Events;

import model.GameCore.FinishingGameCore.SmartRobot;
import model.GameCore.Landscape;
import model.GameCore.LandscapeState;
import org.jetbrains.annotations.NotNull;

import java.util.EventObject;

public class AbstractRobotEvent extends EventObject {
    // ============= ATTRIBUTES =============
    private Class           _landscapeType  = null;     // Type of the landscape which has affected robot
    private LandscapeState  _landscapeState = null;     // State of the landscape which has affected robot

    // ============= OPERATIONS =============
    // ---------- create ----------
    /** Constructor
     * @param source 	    object on which the event initially occurred
     */
    public AbstractRobotEvent(Object source) { super(source); }

    // --------- contract ---------
    // ~~~ type of the landscape ~~~
    /** Set type of the landscape
     * @param landscapeType    type of the landscape
     */
    public void setLandscapeType(@NotNull Class<? extends Landscape> landscapeType) { this._landscapeType = landscapeType; }

    /** Get type of the landscape
     * @return type of the landscape
     */
    public Class getLandscapeType() { return this._landscapeType; }

    // ~~~ state of the landscape ~~~
    /** Set state of the landscape
     * @param landscapeState    state of the landscape
     */
    public void setLandscapeState(@NotNull LandscapeState landscapeState) { this._landscapeState = landscapeState; }

    /** Get state of the landscape
     * @return state of the landscape
     */
    public LandscapeState getLandscapeState() { return this._landscapeState; }

    // -------- comparison --------
    /** Check on equality with other event
     * @param other         exit cell event
     * @return sign of equality with other event
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof AbstractRobotEvent) {
            AbstractRobotEvent otherEvent = (AbstractRobotEvent)other;

            boolean areEqual = (this.source == otherEvent.source);
            areEqual &= (this._landscapeType == otherEvent._landscapeType);
            areEqual &= (this._landscapeState == otherEvent._landscapeState);

            return areEqual;
        }
        else {
            return false;
        }
    }
}
