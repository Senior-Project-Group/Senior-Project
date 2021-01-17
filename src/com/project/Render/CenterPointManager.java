package com.project.Render;

import com.project.BoardController.Location;

public class CenterPointManager {

	private static int defaultDimension = 60;
	
	// This class is responsible for finding the center point for each chess square
	public CenterPointManager() {}
	
	
	// Input is x and z coord for the grid plain
	// Output is a location object that has the center point for that square as x, z
	public Location centerPointAlgorithm(int x, int z) {
		// Start center point at (0,0) is (50, 600);
	
		return new Location(50 + (79 * x), 600 - (79 * z));
	}
	
	public int getDimensionOfPiece() {
		return defaultDimension;
	}
	
}