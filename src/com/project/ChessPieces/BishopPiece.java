package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Render.PiecesTexture;

public class BishopPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	
	public BishopPiece(Location location) {
		isAlive = true;
		hasMovedOnce = false;
		this.location = location;
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
		hasMovedAlready();
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
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		
		return null;
	}

	
}