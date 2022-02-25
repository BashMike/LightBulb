package model.Navigation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST_RectangleSize {
	// ============ test part ============
	// --- tests short description ---
	// 1. Creation;
	// 1.1. Simple creation.
	// 1.1.1. Successful creation 	- simple creation;
	// 1.1.2. Unsuccessful creation - some of the size is negative.

	// ========== CREATION ==========
	// --- SIMPLE CREATION ---
	// Successful creation - SIMPLE creation
	@Test
	void basicConstructor_simpleCreation() {
		RectangleSize rectSize = new RectangleSize(2, 3);
		Assertions.assertEquals(rectSize.width(), 2);
		Assertions.assertEquals(rectSize.height(), 3);
	}

	// Unsuccessful creation - some of the SIZE IS NEGATIVE
    @Test
	void basicConstructor_someOfSizeIsNegative() {
		Exception exception = Assertions.assertThrows(RuntimeException.class, ()->new RectangleSize(-2, 3));
		Assertions.assertNotEquals("", exception.getMessage());
	}
}
