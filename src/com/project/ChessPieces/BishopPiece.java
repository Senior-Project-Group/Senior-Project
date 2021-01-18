package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;
import com.project.TeamController.TeamType;

public class BishopPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive; // Is set when the piece dies
	
	private boolean hasMovedOnce;
	
	private TeamType team;
	
	public BishopPiece(Location location, TeamType team) {
		this.team = team;
		isAlive = true;
		hasMovedOnce = false;
		this.location = location;
		texture = new PiecesTexture(this, getLocation().getX(), getLocation().getZ(), team);
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

	// Gets the texture and other features of the piece for board rendering
	@Override
	public PiecesTexture getTexture() {
		return texture;
	}

	@Override
	public ArrayList<Location> getPossibleMoves() {
		ArrayList<Location> locationsToMove = new ArrayList<Location>();
		
		if(team.equals(TeamType.WHITE)) {
			
			// Right up diagonal
			for(int x = 0; x != 8; x++) {
				Location loc = new Location(getLocation().getX() + x, getLocation().getZ() + x);
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
			
			// Right down diagonal
			for(int x = 0; x != 8; x++) {
				Location loc = new Location(getLocation().getX() + x, getLocation().getZ() - x);
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
			// Left up diagonal
				for(int x = 0; x != 8; x++) {
					Location loc = new Location(getLocation().getX() - x, getLocation().getZ() + x);
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
				
				// Left down diagonal
					for(int x = 0; x != 8; x++) {
						Location loc = new Location(getLocation().getX() - x, getLocation().getZ() - x);
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
			
		}else {
			
			
			
		}
		
		return locationsToMove;
	}

	
}