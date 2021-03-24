package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;

public class DistanceCalculator {

	public DistanceCalculator() {}
	
	
	// Return 1 is in range, return -1 if not
	public int getDistance(Location from, Location to, int checkAmount) {
		if(!Main.getBoardController().isLocationOnBoard(to) || !Main.getBoardController().isLocationOnBoard(from)) {
			return -1;
		}
		
		int xDistance = Math.abs(from.getX() - to.getX());
		int zDistance = Math.abs(from.getZ() - to.getZ());
		
		if(xDistance <= checkAmount && zDistance <= checkAmount) {
			return 1;
		}
		
		return -1;
	}
	
	public boolean checkIfPathIsPossible(Location from, Location to, int checkAmount) {
		int xChange = from.getX() - to.getX();
		int zChange = from.getZ() - to.getZ();
		
		ArrayList<Location> surroundingPieces = new ArrayList<Location>();
		
		int[][] offsets = {
		        {1, 0},
		        {0, 1},
		        {-1, 0},
		        {0, -1},
		        {1, 1},
		        {-1, 1},
		        {-1, -1},
		        {1, -1},
		    };
		
		for(int x = 0; x != 8; x++) {
			int xOffset = offsets[x][0];
			int zOffset = offsets[x][1];
			Location loc = new Location(from.getX() + xOffset, from.getZ() + zOffset);
			if(Main.getBoardController().isLocationOnBoard(loc)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
				if(piece != null) {
					surroundingPieces.add(loc);
				}
			}
		}
		
		
		
		
		return false;
	}
	
}