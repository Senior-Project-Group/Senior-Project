package com.project.AiController;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.PawnPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;

public class PieceInformation {

	private boolean canKill; // If there is a piece at that location
	private IChessPiece piece; // Piece to move
	private Location location; // Location to move too
	private IChessPiece pieceAtMoveLocation;
	
	public PieceInformation(IChessPiece piece, Location location, boolean canKill, IChessPiece pieceAtLocation) {
		this.piece = piece;
		this.canKill = canKill;
		this.location = location;
		
		this.pieceAtMoveLocation = pieceAtLocation;
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
	
	// Return the rank of the move
	public int getRankOfMove() {
		
		// Return 0 if there is no piece at the location
		if(pieceAtMoveLocation == null) return 0; 
		
		if(pieceAtMoveLocation instanceof PawnPiece) {
			return 1;
		}else if(pieceAtMoveLocation instanceof KnightPiece) {
			return 2;
		}else if(pieceAtMoveLocation instanceof RookPiece) { // Both rook and bishop are same rank
			return 3;
		}else if(pieceAtMoveLocation instanceof BishopPiece) {
			return 3;
		}else if(pieceAtMoveLocation instanceof QueenPiece) {
			return 4;
		}else {
			return 5; // Last piece is the king
		}
	}
}
