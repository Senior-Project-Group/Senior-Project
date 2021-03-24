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
		
		ArrayList<Location> surroundingPiecesThatAreNull = new ArrayList<Location>();
		
		for(int x = 0; x != 9; x++) {
			for(int y = 0; y != 9; y++) {
				Location location = new Location(x, y);
				if(Main.getBoardController().isLocationOnBoard(location)) {
					if(getDistance(from, location, 3) == 1) {
						IChessPiece at = Main.getBoardController().getPieceAtLocation(location);
						if(at == null) {
							surroundingPiecesThatAreNull.add(location);
						}
					}
					
				}
				
			}
		}
		
		
		
		return false;
	}
	
}