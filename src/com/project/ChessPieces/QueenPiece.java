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
		
		for(int x = 0; x != 9; x++) {
			for(int y = 0; y != 9; y++) {
				Location location = new Location(x, y);
				if(Main.getBoardController().isLocationOnBoard(location)) {
					DistanceCalculator cal = new DistanceCalculator();
					if(cal.getDistance(getLocation(), location, 3) == 1) {
						IChessPiece at = Main.getBoardController().getPieceAtLocation(location);
						if(at != null) {
							if(!at.getTeamType().equals(getTeamType())) {
								locationsToMove.add(location);
							}	
						}else {
							locationsToMove.add(location);
						}
					}
					
					
				}
				
			}
		}
		
		return locationsToMove;
	}

	
}