package model.GameCore;

import org.jetbrains.annotations.NotNull;

public interface UpdatableLandscape extends Landscape {
    // ============= OPERATIONS =============
    // --------- contract ---------
    /** Update landscape based on given season
     * @param season         season
     */
    void update(@NotNull Game.Season season);
}
