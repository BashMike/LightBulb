package model.MazeDesigners;

import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.GameCore.MazeDesigner;
import model.Navigation.CellPosition;
import model.Navigation.RectangleSize;

public class ForTestMazeDesigner extends MazeDesigner {
    // ============= OPERATIONS =============
    // ---------- create ----------
    public ForTestMazeDesigner() {
        this._setFieldSize(new RectangleSize(3, 3));
        this._setExitCellPosition(new CellPosition(2, 1));

        this._addGameElementInfo(SmartRobot.class, new CellPosition(1, 1));
        this._addGameElementInfo(SillyRobot.class, new CellPosition(0, 2));
    }
}
