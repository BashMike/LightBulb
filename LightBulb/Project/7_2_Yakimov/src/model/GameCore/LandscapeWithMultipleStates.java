package model.GameCore;

import org.jetbrains.annotations.NotNull;

public interface LandscapeWithMultipleStates extends UpdatableLandscape {
    // ============= OPERATIONS =============
    // --------- contract ---------
    /** Get state of landscape
     * @return state of landscape
     */
    @NotNull
    LandscapeState state();
}