package com.project.AiController;

import com.project.BoardController.Location;
import com.project.ChessPieces.IChessPiece;

public class PieceInformation {

	private boolean canKill;
	private IChessPiece piece;
	private Location location;
	public PieceInformation(IChessPiece piece, Location location, boolean canKill) {
		this.piece = piece;
		this.canKill = canKill;
		this.location = location;
	}
	
	public IChessPiece getPiece() {
		return piece;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean canKillPiece() {
		return canKill;
	}
}
