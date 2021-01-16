package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;

public class RookPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	public RookPiece(Location location) {
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
	
	@Override
	public ArrayList<Location> getPossibleMoves() {
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		// Up
		for(int x = 1; x != 8; x++) {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() + x);
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(this)) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// Down
		for(int x = 0; x != 8; x++) {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() - x);
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(this)) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// To the right
		for(int x = 0; x != 8; x++) {
			Location loc = new Location(getLocation().getX() + x, getLocation().getZ());
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(this)) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// To the left
		for(int x = 0; x != 8; x++) {
			Location loc = new Location(getLocation().getX() - x, getLocation().getZ());
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(this)) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		
		return locationsToMove;
	}
	
}