package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;
import com.project.TeamController.TeamType;

public class PawnPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	private TeamType team;
	
	public PawnPiece(Location location, TeamType team) {
		this.team = team;
		isAlive = true;
		hasMovedOnce = false;
		this.location = location;
		texture = new PiecesTexture(this, getLocation().getX(), getLocation().getZ(), team);
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
		getTexture().removeTextureFromBoard();
		Main.getBoardController().removePieceFromBoard(this);
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(int x, int z) {
		location = new Location(x, z);
		setHasMovedOnce();
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
		setHasMovedOnce();
	}
	
	@Override
	public PiecesTexture getTexture() {
		return texture;
	}
	
	@Override
	public ArrayList<Location> getPossibleMoves() {
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		if(team.equals(TeamType.WHITE)) {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() + 1);
			if(Main.getBoardController().getPieceAtLocation(loc) == null) {
				locationsToMove.add(loc);
			}
			// Special move
			if(!hasMovedAlready()) {
				Location loc1 = new Location(getLocation().getX(), getLocation().getZ() + 2);
				if(Main.getBoardController().isLocationOnBoard(loc1)) {
					if(Main.getBoardController().getPieceAtLocation(loc1) == null) {
						locationsToMove.add(loc1);
					}
				}
			}
			
			// Diagnonals
			Location diag1 = new Location(getLocation().getX() + 1, getLocation().getZ() + 1);
			if(Main.getBoardController().isLocationOnBoard(diag1)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag1);
				if(piece != null) {
					if(Main.getBoardController().isLocationOnBoard(diag1)) {
						if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
							locationsToMove.add(diag1);
						}
					}
				}
			}
			
			Location diag2 = new Location(getLocation().getX() - 1, getLocation().getZ() + 1);
			if(Main.getBoardController().isLocationOnBoard(diag2)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag2);
				if(Main.getBoardController().isLocationOnBoard(diag2)) {
					if(piece != null) {
						if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
							locationsToMove.add(diag2);
						}
					}
				}
			}
		}else {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() - 1);
			if(Main.getBoardController().getPieceAtLocation(loc) == null) {
				if(Main.getBoardController().isLocationOnBoard(loc)) {
					locationsToMove.add(loc);
				}
			}
			// Special move
			if(!hasMovedAlready()) {
				Location loc1 = new Location(getLocation().getX(), getLocation().getZ() - 2);
				if(Main.getBoardController().getPieceAtLocation(loc1) == null) {
					if(Main.getBoardController().isLocationOnBoard(loc1)) {
						locationsToMove.add(loc1);
					}
				}
			}
			
			// Diagnonals
			Location diag1 = new Location(getLocation().getX() - 1, getLocation().getZ() - 1);
			if(Main.getBoardController().isLocationOnBoard(diag1)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag1);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
						locationsToMove.add(diag1);
					}
				}
			}
			
			Location diag2 = new Location(getLocation().getX() + 1, getLocation().getZ() - 1);
			if(Main.getBoardController().isLocationOnBoard(diag2)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(diag2);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
						if(Main.getBoardController().isLocationOnBoard(diag2)) {
							locationsToMove.add(diag2);
						}
					}
				}
			}
		}
		
		
		return locationsToMove;
	}
	
}
