package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;


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
		hasMovedAlready();
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
		hasMovedAlready();
	}
	
	@Override
	public PiecesTexture getTexture() {
		return texture;
	}
	
	// TODO Make it so the king can't move itself into checkmate
	// TODO Make it so the king can be castled
	@Override
	public ArrayList<Location> getPossibleMoves() {
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		int[][] offsets = {
		        {1, 0},
		        {0, 1},
		        {-1, 0},
		        {0, -1},
		        {1, 1},
		        {-1, 1},
		        {-1, -1},
		        {1, -1}
		    };
		
		for(int x = 0; x != 8; x++) {
			int xOffset = offsets[x][0];
			int zOffset = offsets[x][1];
			Location loc = new Location(getLocation().getX() + xOffset, getLocation().getZ() + zOffset);
			if(Main.getBoardController().isLocationOnBoard(loc)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(this)) {
						locationsToMove.add(loc);
					}
				}
			}
		}
		
		// Add castling here
		if(!hasMovedAlready()) {
			
		}
		
		return locationsToMove;
	}

}