package com.project.ChessPieces;

import com.project.BoardController.Location;

public class BishopPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	
	public BishopPiece(Location location) {
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

	@Override
	public PiecesTexture getTexture() {
		return texture;
	}
	
}