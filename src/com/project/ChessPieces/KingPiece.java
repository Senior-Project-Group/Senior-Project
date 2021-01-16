package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;

public class KingPiece implements IChessPiece{

	private Location location;
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	public KingPiece(Location location) {
		isAlive = true;
		hasMovedOnce = false;
		this.location = location;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	@Override
	public boolean hasMovedAlready() {
		return hasMovedOnce;
	}

	@Override
	public void setHasMovedOnce() {
		this.hasMovedOnce = true;
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
	
	@Override
	public ArrayList<Location> getPossibleMoves() {
		// TODO Make method to detemine where the piece can move 
		return null;
	}

}