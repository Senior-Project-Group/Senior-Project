package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;

public class PawnPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	public PawnPiece(Location location) {
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
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		Location loc = new Location(getLocation().getX() + 1, getLocation().getZ());
		if(Main.getBoardController().getPieceAtLocation(loc) == null) {
			locationsToMove.add(loc);
		}
		// Special move
		if(hasMovedAlready()) {
			Location loc1 = new Location(getLocation().getX() + 2, getLocation().getZ());
			if(Main.getBoardController().getPieceAtLocation(loc1) == null) {
				locationsToMove.add(loc1);
			}
		}
		
		// Diagnonals
		Location diag1 = new Location(getLocation().getX() + 1, getLocation().getZ() + 1);
		if(Main.getBoardController().isLocationOnBoard(diag1)) {
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag1);
			if(piece != null) {
				if(!Main.getBoardController().getTeamPieceBelongsTo(piece).equals(Main.getBoardController().getTeamPieceBelongsTo(this))) {
					locationsToMove.add(diag1);
				}
			}
		}
		
		Location diag2 = new Location(getLocation().getX() + 1, getLocation().getZ() - 1);
		if(Main.getBoardController().isLocationOnBoard(diag2)) {
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag2);
			if(piece != null) {
				if(!Main.getBoardController().getTeamPieceBelongsTo(piece).equals(Main.getBoardController().getTeamPieceBelongsTo(this))) {
					locationsToMove.add(diag2);
				}
			}
		}
		
		
		return locationsToMove;
	}
	
}
