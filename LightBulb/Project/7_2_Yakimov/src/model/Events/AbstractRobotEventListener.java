package model.Events;

public interface AbstractRobotEventListener {
    // ============= OPERATIONS =============
    /** Handle event when robot has being affected by landscape
     * @param event - event information
     */
    void robotIsAffectedByLandscape(AbstractRobotEvent event);

    /** Handle event when robot has done some action by timer
     * @param event - event information
     */
    void robotHasDoneActionByTimer(AbstractRobotEvent event);
}