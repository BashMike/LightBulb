package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_RectangleArea {
	// ============ test part ============
	// --- tests short description ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation - simple creation.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		RectangleArea area = new RectangleArea(new CellPosition(2, 3), new RectangleSize(5, 6));

		Assertions.assertEquals(area.leftTopPosition(), new CellPosition(2, 3));
		Assertions.assertEquals(area.size(), new RectangleSize(5, 6));
	}
}
