package model.MazeDesigners;

import model.GameCore.FinishingGameCore.Sewer;
import model.GameCore.FinishingGameCore.SillyRobot;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.GameCore.MazeDesigner;
import model.GameCore.SingleLandscape;
import model.Landscapes.Sand;
import model.Navigation.BetweenCellsPosition;
import model.Navigation.CellPosition;
import model.Navigation.Direction;
import model.Navigation.RectangleSize;

import java.util.ArrayList;

public class SimpleMazeDesigner extends MazeDesigner {
	// ============= OPERATIONS =============
	// ---------- create ----------
	/** Basic constructor
	 */
	public SimpleMazeDesigner() {
		this._setFieldSize(new RectangleSize(8, 6));
		this._setExitCellPosition(new CellPosition(2, 1));

		this._addWallInfo(new BetweenCellsPosition(new CellPosition(3, 3), Direction.UP));
		this._addWallInfo(new BetweenCellsPosition(new CellPosition(3, 3), Direction.RIGHT));
		this._addWallInfo(new BetweenCellsPosition(new CellPosition(4, 4), Direction.DOWN));
		this._addWallInfo(new BetweenCellsPosition(new CellPosition(4, 4), Direction.LEFT));

		ArrayList<CellPosition> sewerElementsCellPositions = new ArrayList<>();
		sewerElementsCellPositions.add(new CellPosition(4, 1));
		this._addMultipleLandscapeInfo(Sewer.class, sewerElementsCellPositions);
		this._addSingleLandscapeInfo(Sand.class, new CellPosition(4, 2));

		this._addGameElementInfo(SmartRobot.class, new CellPosition(1, 1));
		this._addGameElementInfo(SillyRobot.class, new CellPosition(0, 2));
	}
}
