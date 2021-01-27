package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;
import com.project.TeamController.TeamType;

public class QueenPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	private TeamType team;
	
	public QueenPiece(Location location, TeamType team) {
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
	
	public TeamType getTeamType() {
		return team;
	}
	
	@Override
	public ArrayList<Location> getPossibleMoves() {
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		
		/* Check for bishop piece moves */
		
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX() + x, getLocation().getZ() + x);
			if(Main.getBoardController().isLocationOnBoard(loc)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
						locationsToMove.add(loc);
					}
					break;
				}else {
					locationsToMove.add(loc);
				}
			}
		}
		
		// Right down diagonal
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX() + x, getLocation().getZ() - x);
			if(Main.getBoardController().isLocationOnBoard(loc)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
						locationsToMove.add(loc);
					}
					break;
				}else {
					locationsToMove.add(loc);
				}
			}
			
		}
		
		// Left up diagonal
			for(int x = 1; x != 9; x++) {
				Location loc = new Location(getLocation().getX() - x, getLocation().getZ() + x);
				if(Main.getBoardController().isLocationOnBoard(loc)) {
					IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
					if(piece != null) {
						if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
							locationsToMove.add(loc);
						}
						break;
					}else {
						locationsToMove.add(loc);
					}
				}
			}
			
			// Left down diagonal
				for(int x = 1; x != 9; x++) {
					Location loc = new Location(getLocation().getX() - x, getLocation().getZ() - x);
					if(Main.getBoardController().isLocationOnBoard(loc)) {
						IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
						if(piece != null) {
							if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
								locationsToMove.add(loc);
							}
							break;
						}else {
							locationsToMove.add(loc);
						}
								
					}
								
				}
		
				
		/* Check for rook piece moves */

		// Up
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() + x);
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// Down
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX(), getLocation().getZ() - x);
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// To the right
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX() + x, getLocation().getZ());
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
					locationsToMove.add(loc);
				}
				break;
			}else {
				locationsToMove.add(loc);
			}
		}
		
		// To the left
		for(int x = 1; x != 9; x++) {
			Location loc = new Location(getLocation().getX() - x, getLocation().getZ());
			if(!Main.getBoardController().isLocationOnBoard(loc)) break;
			IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
			if(piece != null) {
				if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
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