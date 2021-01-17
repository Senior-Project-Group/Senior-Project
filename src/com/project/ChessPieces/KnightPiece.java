package com.project.ChessPieces;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.Main.Main;
import com.project.Render.PiecesTexture;
import com.project.TeamController.TeamType;

public class KnightPiece implements IChessPiece{

	private Location location;
	
	private PiecesTexture texture;
	
	private boolean isAlive;
	
	private boolean hasMovedOnce;
	
	private TeamType team;
	
	public KnightPiece(Location location, TeamType team) {
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
		Main.getBoardController().getTeamPieceBelongsTo(this).removePiece(this);
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
		
			int[][] offsets = {
			        {2, 1},
			        {2, -1},
			        {-2, 1},
			        {-2, -1},
			        
			        {1, 2},
			        {-1, 2},
			        {1, -2},
			        {-1, -2}
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
					}else {
						locationsToMove.add(loc);
					}
				}
			}
			
		return locationsToMove;
	}
	
}