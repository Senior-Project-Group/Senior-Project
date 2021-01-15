package com.project.ChessPieces;

import com.project.BoardController.Location;

public class PawnPiece implements IChessPiece{

	private Location location;
	
	private boolean isAlive;
	
	public PawnPiece(Location location) {
		isAlive = true;
		this.location = location;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	@Override
	public void destroyPiece() {
		this.isAlive = false;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(int x, int z) {
		location = new Location(x, z);
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
