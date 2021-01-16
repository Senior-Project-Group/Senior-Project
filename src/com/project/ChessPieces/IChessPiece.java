package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;

public interface IChessPiece {

	public boolean hasMovedAlready(); // This is to check if the piece has moved once already
	
	public void setHasMovedOnce(); // Set the piece to have moved once already
	
	public boolean isAlive();
	
	public void destroyPiece();
	
	public Location getLocation();
	
	public void setLocation(int x, int z);
	
	public void setLocation(Location location);
	
	public PiecesTexture getTexture();
	
	public ArrayList<Location> getPossibleMoves();
	
}