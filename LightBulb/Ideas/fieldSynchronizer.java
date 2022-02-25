class FieldWidget extends JPanel {
	private FieldSynchronizer 				_fieldSycnhronizer;
	private ArrayList<GameElementWidget> 	_gameElementWidgets;

	private RectangleArea					_viewArea; // Can't have even size
}

class FieldSynchronizer {
	private FieldWidget _fieldWidget;
	private Field 		_field;

	/** Synchronize field widget with current state of field
	*/
	void synchronize() {
		// Clear cell widgets of field widget
		this._clearCellsOfFieldWidget();

		// Move view area at the center of smart robot in the field
		AbstractCell smartRobotCell = this._field.getSmartRobot().cell;	
		CellPosition smartRobotCellPos = this._field.cellPosition(smartRobotCell);

		int viewAreaX = smartRobotCellPos.x - this._fieldWidget.viewArea().width() / 2;
		int viewAreaY = smartRobotCellPos.y - this._fieldWidget.viewArea().height() / 2;
		this._fieldWidget.setViewAreaPosition(new CellPosition(viewAreaX, viewAreaY));

		// Get cells from field at given view area
		ArrayList<AbstractCell> viewAreaCells = this._field.cells(this._fieldWidget.viewArea());

		// For each cell in gotten cells
		for (AbstractCell cell : viewAreaCells) {
			// Get all game elements from cell
			ArrayList<GameElement> gameElements = cell.getGameElements(GameElement.class);

			// Get corresponding game element widgets of gotten game elements
			ArrayList<GameElementWidget> gameElementWidgets;

			for (GameElement gameElement : gameElements) {
				gameElementWidget.add(this._fieldWidget.gameElementWidget(gameElement));
			}

			// Get corresponding cell widget
			CellPosition cellPosition = this._field.cellPosition(cell);

			CellWidget cellWidget = this._fieldWidget.getCellWidgetOfCell(cellPosition);

			// Place gotten game element widgets at corresponding cell
			for (GameElementWidget gameElementWidget : gameElementWidgets) {
				cellWidget.add(gameElementWidget);
			}
		}
	}

	private void _clearCellsOfFieldWidget();
}

class Field {}