package model.GameCore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class MultipleLandscape implements Landscape {
    // ============= ATTRIBUTES =============
    // ~~~ main ~~~
    protected LandscapeActions              _actions                = new LandscapeActions(this);     // All possible landscape actions
    protected ArrayList<SingleLandscape>    _innerElements          = new ArrayList<>();                    // Inner elements

    // ============= OPERATIONS =============
    // ---------- create ----------
    /** Basic constructor
     */
    public MultipleLandscape() {}

    // --------- contract ---------
    /** Place element of multiple landscape on the given cell
     * @param cell      cell on which multiple landscape will place its element
     * @return sign of successful placement
     */
    final boolean fillCell(@NotNull AbstractCell cell) {
        boolean success = true;

        // Create and place element of multiple landscape on the given cell
        SingleLandscape innerElement = this._createAndPlaceInnerElement(cell);

        // Remember created element of multiple landscape
        success &= this._innerElements.add(innerElement);

        return success;
    }

    /** Place element of multiple landscape on the given cell
     * @param cell      cell on which multiple landscape will place its element
     * @return sign of successful placement
     */
    @NotNull
    protected SingleLandscape _createAndPlaceInnerElement(@NotNull AbstractCell cell) {
        SingleLandscape result = null;
        // Create inner element
        SingleLandscape innerElement = new SingleLandscape(this);

        // Try to place inner element on cell
        boolean successfulPlacing = cell.placeGameElement(innerElement);
        if (successfulPlacing) {
            result = innerElement;
        }

        return result;
    }
}
