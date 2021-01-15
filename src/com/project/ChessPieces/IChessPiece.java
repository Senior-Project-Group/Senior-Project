package com.project.ChessPieces;

import com.project.BoardController.Location;

public interface IChessPiece {

	public boolean isAlive();
	
	public void destroyPiece();
	
	public Location getLocation();
	
	public void setLocation(int x, int z);
	
	public void setLocation(Location location);
	
}