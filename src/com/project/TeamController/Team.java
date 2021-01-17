package com.project.TeamController;

import java.util.ArrayList;

import com.project.BoardController.Location;
import com.project.ChessPieces.BishopPiece;
import com.project.ChessPieces.IChessPiece;
import com.project.ChessPieces.KingPiece;
import com.project.ChessPieces.KnightPiece;
import com.project.ChessPieces.PawnPiece;
import com.project.ChessPieces.QueenPiece;
import com.project.ChessPieces.RookPiece;

public class Team {

	private ArrayList<IChessPiece> pieces;
	
	private TeamType teamType;
	
	public Team(TeamType type) {
		teamType = type;
		pieces = new ArrayList<IChessPiece>();
		setPieces();
	}
	
	private void setPieces() {
		// TODO Create way to set pieces
		
		if(getTeamType().equals(TeamType.BLACK)) {
			// Set the location pieces for black
			
			// Set bottom row for black
			pieces.add(new RookPiece(new Location(0, 7), TeamType.BLACK));
			pieces.add(new KnightPiece(new Location(1, 7), TeamType.BLACK));
			pieces.add(new BishopPiece(new Location(2, 7), TeamType.BLACK));
			pieces.add(new QueenPiece(new Location(3, 7), TeamType.BLACK));
			pieces.add(new KingPiece(new Location(4, 7), TeamType.BLACK));
			pieces.add(new BishopPiece(new Location(5, 7), TeamType.BLACK));
			pieces.add(new KnightPiece(new Location(6, 7), TeamType.BLACK));		
			pieces.add(new RookPiece(new Location(7, 7), TeamType.BLACK));
			
			// Set top row for black
			for(int x = 0; x != 8; x++) {
				pieces.add(new PawnPiece(new Location(x, 6), TeamType.BLACK));
			}
			
			
		}else {
			// Set the location pieces for white
			
			// Bottom row for white
			pieces.add(new RookPiece(new Location(0, 0), TeamType.WHITE));
			pieces.add(new KnightPiece(new Location(1, 0), TeamType.WHITE));
			pieces.add(new BishopPiece(new Location(2, 0), TeamType.WHITE));
			pieces.add(new QueenPiece(new Location(3, 0), TeamType.WHITE));
			pieces.add(new KingPiece(new Location(4, 0), TeamType.WHITE));
			pieces.add(new BishopPiece(new Location(5, 0), TeamType.WHITE));
			pieces.add(new KnightPiece(new Location(6, 0), TeamType.WHITE));
			pieces.add(new RookPiece(new Location(7, 0), TeamType.WHITE));
			
			// Top row for white (pawns)
			for(int x = 0; x != 8; x++) {
				pieces.add(new PawnPiece(new Location(x, 1), TeamType.WHITE));
			}
			
		}
		
	}
	
	public ArrayList<IChessPiece> getChessPieces(){
		return pieces;
	}
	
	public void removePiece(IChessPiece piece) {
		if(pieces.contains(piece)) {
			pieces.remove(piece);
		}
	}
	
	public TeamType getTeamType() {
		return teamType;
	}
	
}