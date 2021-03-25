package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;
import com.project.TeamController.TeamType;

public class RookPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	private TeamType team;
	
	private Location startLocation;
	
	public RookPiece(Location location, TeamType team) {
		this.team = team;
		isAlive = true;
		hasMovedOnce = false;
		this.location = location;
		texture = new PiecesTexture(this, getLocation().getX(), getLocation().getZ(), team);
		startLocation = location;
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
	public Location getStartLocation() {
		return startLocation;
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
		
		int[][] offsets = {
		        {1, 0},
		        {0, 1},
		        {-1, 0},
		        {0, -1},
		        {1, 1},
		        {-1, 1},
		        {-1, -1},
		        {1, -1},
		       		        
		    };
		
		for(int x = 0; x != 8; x++) {
			int xOffset = offsets[x][0];
			int zOffset = offsets[x][1];
			Location loc = new Location(getLocation().getX() + xOffset, getLocation().getZ() + zOffset);
			if(Main.getBoardController().isLocationOnBoard(loc)) {
				IChessPiece piece = Main.getBoardController().getPieceAtLocation(loc);
				if(piece != null) {
					if(Main.getBoardController().getTeamPieceBelongsTo(piece) != Main.getBoardController().getTeamPieceBelongsTo(Main.getBoardController().getPieceAtLocation(getLocation()))) {
						locationsToMove.add(loc);
					}
				}else {
					locationsToMove.add(loc);
				}
			}
		}
		
		ArrayList<Location> attackLocations = new DistanceCalculator().findPossiblePaths(this, 2);
		for(Location attack : attackLocations) {
			IChessPiece atLocation = Main.getBoardController().getPieceAtLocation(attack);
			if(atLocation != null) {
				if(!atLocation.getTeamType().equals(this.getTeamType())) {
					locationsToMove.add(attack);
				}
				
			}
		}
		
		
		
		
		return locationsToMove;
	}
	
}